package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.common.vo.WechatLoginRequest;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.entity.User;
import com.example.mzt_server.service.ICaptchaService;
import com.example.mzt_server.service.IAuthService;
import com.example.mzt_server.service.ISysUserService;
import com.example.mzt_server.service.IUserService;
import com.example.mzt_server.service.IWechatService;
import com.example.mzt_server.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IUserService miniappUserService;

    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IWechatService wechatService;

    /**
     * 登录
     *
     * @param request 登录请求
     * @return 登录结果
     */
    @Override
    public LoginResult login(LoginRequest request) {
        // 1. 验证验证码
        if (!captchaService.validateCaptcha(request.getCaptchaKey(), request.getCaptchaCode())) {
            throw new BusinessException(ErrorEnum.CAPTCHA_ERROR);
        }

        // 2. 查询用户
        SysUser user = userService.getByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorEnum.USER_NOT_FOUND);
        }

        // 3. 验证密码
        if (!userService.verifyPassword(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        }

        // 4. 验证用户状态
        if (user.getStatus() != 1) {
            throw new BusinessException(ErrorEnum.ACCOUNT_DISABLED);
        }

        // 5. 生成令牌
        return generateTokens(user);
    }

    /**
     * 微信小程序登录
     *
     * @param request 微信登录请求
     * @return 登录结果
     */
    @Override
    public LoginResult wechatLogin(WechatLoginRequest request) {
        // 1. 通过code获取微信用户信息（包含openid和session_key）
        Map<String, String> wechatUserInfo = wechatService.getWechatUserInfo(request.getCode());
        String openid = wechatUserInfo.get("openid");
        String sessionKey = wechatUserInfo.get("session_key");
        String unionid = wechatUserInfo.get("unionid");
        
        // 2. 根据openid查找用户
        User user = miniappUserService.getByOpenid(openid);
        
        // 3. 解密手机号（如果提供了加密数据）
        String phoneNumber = null;
        if (StringUtils.hasText(request.getEncryptedData()) && 
            StringUtils.hasText(request.getIv()) && 
            StringUtils.hasText(sessionKey)) {
            try {
                phoneNumber = wechatService.decryptPhoneNumber(
                    request.getEncryptedData(), 
                    request.getIv(), 
                    sessionKey
                );
            } catch (Exception e) {
                // 手机号解密失败不影响登录，只记录日志
                System.out.println("手机号解密失败: " + e.getMessage());
            }
        }
        
        // 4. 如果用户不存在，则创建新用户
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setUnionid(unionid);
            user.setNickname(StringUtils.hasText(request.getNickName()) ? request.getNickName() : "微信用户");
            user.setAvatar(request.getAvatarUrl());
            user.setPhone(phoneNumber);
            user.setGender(0); // 默认未知
            
            // 保存用户
            miniappUserService.save(user);
        } else {
            // 5. 如果用户存在，更新用户信息
            boolean needUpdate = false;
            
            if (StringUtils.hasText(request.getNickName()) && !request.getNickName().equals(user.getNickname())) {
                user.setNickname(request.getNickName());
                needUpdate = true;
            }
            if (StringUtils.hasText(request.getAvatarUrl()) && !request.getAvatarUrl().equals(user.getAvatar())) {
                user.setAvatar(request.getAvatarUrl());
                needUpdate = true;
            }
            if (StringUtils.hasText(phoneNumber) && !phoneNumber.equals(user.getPhone())) {
                user.setPhone(phoneNumber);
                needUpdate = true;
            }
            if (StringUtils.hasText(unionid) && !unionid.equals(user.getUnionid())) {
                user.setUnionid(unionid);
                needUpdate = true;
            }
            
            if (needUpdate) {
                miniappUserService.updateById(user);
            }
        }
        
        // 6. 生成令牌（这里需要调整生成令牌的方法，因为User和SysUser结构不同）
        return generateMiniappUserTokens(user);
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    @Override
    public LoginResult refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new BusinessException(ErrorEnum.INVALID_TOKEN);
        }

        // 解析令牌
        try {
            // 获取用户ID
            Long userId = jwtUtils.parseToken(refreshToken).get("userId", Long.class);
            
            // 查询用户
            SysUser user = userService.getById(userId);
            if (user == null) {
                throw new BusinessException(ErrorEnum.USER_NOT_FOUND);
            }
            
            // 验证用户状态
            if (user.getStatus() != 1) {
                throw new BusinessException(ErrorEnum.ACCOUNT_DISABLED);
            }
            
            // 生成新令牌
            return generateTokens(user);
        } catch (Exception e) {
            throw new BusinessException(ErrorEnum.INVALID_TOKEN);
        }
    }

    /**
     * 获取验证码
     *
     * @return 验证码信息
     */
    @Override
    public CaptchaInfo getCaptcha() {
        return captchaService.generateCaptcha();
    }

    /**
     * 注销登录
     *
     * @param token 访问令牌
     * @return 是否成功
     */
    @Override
    public boolean logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (token == null || !jwtUtils.validateToken(token)) {
            return false;
        }
        
        try {
            // 解析令牌，获取令牌ID
            Claims claims = jwtUtils.parseToken(token);
            String tokenId = claims.getId();
            
            // 将令牌加入黑名单，设置过期时间
            long expiration = claims.getExpiration().getTime() - System.currentTimeMillis();
            if (expiration > 0) {
                // 将令牌加入Redis黑名单，过期时间与令牌剩余时间一致
                redisTemplate.opsForValue().set("jwt:blacklist:" + tokenId, "1", expiration, TimeUnit.MILLISECONDS);
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 生成令牌
     *
     * @param user 用户信息
     * @return 登录结果
     */
    private LoginResult generateTokens(SysUser user) {
        // 获取用户角色
        List<String> roles = userService.getUserRoles(user.getId());
        
        // 生成令牌
        String accessToken = jwtUtils.generateAccessToken(user.getUsername(), roles, user.getId());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername(), roles, user.getId());
        
        // 构建返回结果
        LoginResult result = new LoginResult();
        result.setAccessToken(accessToken);
        result.setRefreshToken(refreshToken);
        result.setTokenType("Bearer");
        result.setExpiresIn(jwtUtils.getAccessTokenExpiration());
        
        return result;
    }
    
    /**
     * 为小程序用户生成令牌
     */
    private LoginResult generateMiniappUserTokens(User user) {
        // 小程序用户暂时没有角色系统，使用空角色列表
        List<String> roles = List.of("USER");
        
        // 生成令牌，使用openid作为用户名
        String accessToken = jwtUtils.generateAccessToken(user.getOpenid(), roles, user.getId());
        String refreshToken = jwtUtils.generateRefreshToken(user.getOpenid(), roles, user.getId());
        
        // 构建返回结果
        LoginResult result = new LoginResult();
        result.setAccessToken(accessToken);
        result.setRefreshToken(refreshToken);
        result.setTokenType("Bearer");
        result.setExpiresIn(jwtUtils.getAccessTokenExpiration());
        
        return result;
    }
} 