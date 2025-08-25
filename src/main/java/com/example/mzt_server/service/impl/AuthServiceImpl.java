package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.common.vo.RegisterRequest;
import com.example.mzt_server.common.vo.WechatLoginRequest;
import com.example.mzt_server.common.vo.MiniappLoginRequest;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.entity.User;
import com.example.mzt_server.service.CaptchaService;
import com.example.mzt_server.service.AuthService;
import com.example.mzt_server.service.SysUserService;
import com.example.mzt_server.service.UserService;
import com.example.mzt_server.service.WechatService;
import com.example.mzt_server.service.SmsService;
import com.example.mzt_server.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private UserService miniappUserService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private SmsService smsService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    /**
     * 手机号快速注册
     *
     * @param request 注册请求
     * @return 登录结果
     */
    @Override
    public LoginResult register(RegisterRequest request) {
        // 1. 验证短信验证码
        if (!smsService.validateLoginCode(request.getPhone(), request.getSmsCode(), request.getSmsKey())) {
            throw new BusinessException(ErrorEnum.SMS_CODE_ERROR);
        }

        // 2. 检查手机号是否已注册
        User existingUser = miniappUserService.getByPhone(request.getPhone());
        if (existingUser != null) {
            throw new BusinessException(ErrorEnum.PHONE_ALREADY_EXISTS);
        }

        // 3. 创建新用户
        User user = new User();
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname("用户" + request.getPhone().substring(7)); // 默认昵称
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 4. 保存用户
        miniappUserService.save(user);

        // 5. 生成令牌
        return generateMiniappUserTokens(user);
    }

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
                 log.warn("手机号解密失败: {}", e.getMessage());
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
            if (phoneNumber != null && StringUtils.hasText(phoneNumber) && !phoneNumber.equals(user.getPhone())) {
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
     * 小程序登录（支持多种登录方式）
     *
     * @param request 小程序登录请求
     * @return 登录结果
     */
    @Override
    public LoginResult miniappLogin(MiniappLoginRequest request) {
        switch (request.getLoginType()) {
            case "wechat":
                return handleWechatLogin(request);
            case "password":
                return handlePasswordLogin(request);
            case "sms":
                return handleSmsLogin(request);
            default:
                throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "不支持的登录类型: " + request.getLoginType());
        }
    }

    /**
     * 处理微信登录
     */
    private LoginResult handleWechatLogin(MiniappLoginRequest request) {
        if (!StringUtils.hasText(request.getCode())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "微信登录code不能为空");
        }

        // 1. 通过code获取微信用户信息
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
                log.warn("手机号解密失败: {}", e.getMessage());
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
            if (phoneNumber != null && StringUtils.hasText(phoneNumber) && !phoneNumber.equals(user.getPhone())) {
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
        
        return generateMiniappUserTokens(user);
    }

    /**
     * 处理账号密码登录
     */
    private LoginResult handlePasswordLogin(MiniappLoginRequest request) {
        if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "用户名和密码不能为空");
        }

        // 1. 根据用户名查找用户
        User user = miniappUserService.getByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorEnum.USER_NOT_FOUND);
        }

        // 2. 验证密码（这里需要实现密码验证逻辑）
        if (!miniappUserService.verifyPassword(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        }

        return generateMiniappUserTokens(user);
    }

    /**
     * 处理手机验证码登录
     */
    private LoginResult handleSmsLogin(MiniappLoginRequest request) {
        if (!StringUtils.hasText(request.getPhone()) || !StringUtils.hasText(request.getSmsCode()) || !StringUtils.hasText(request.getSmsKey())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "手机号、验证码和验证码key不能为空");
        }

        // 1. 验证短信验证码
        if (!smsService.validateLoginCode(request.getPhone(), request.getSmsCode(), request.getSmsKey())) {
            throw new BusinessException(ErrorEnum.CAPTCHA_ERROR.getCode(), "验证码错误或已过期");
        }

        // 2. 根据手机号查找用户
        User user = miniappUserService.getByPhone(request.getPhone());
        if (user == null) {
            // 3. 如果用户不存在，创建新用户
            user = new User();
            user.setPhone(request.getPhone());
            user.setNickname("用户" + request.getPhone().substring(7)); // 使用手机号后4位作为昵称
            user.setGender(0); // 默认未知
            
            miniappUserService.save(user);
        }

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
    
    @Override
    public boolean resetPassword(com.example.mzt_server.common.vo.ResetPasswordRequest request) {
        // 1. 参数验证
        if (request == null) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        
        String phone = request.getPhone();
        String smsCode = request.getSmsCode();
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();
        String smsKey = request.getSmsKey();
        
        // 2. 验证必填参数
        if (!StringUtils.hasText(phone) || !StringUtils.hasText(smsCode) || 
            !StringUtils.hasText(newPassword) || !StringUtils.hasText(confirmPassword) || 
            !StringUtils.hasText(smsKey)) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        
        // 3. 验证两次密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(ErrorEnum.PASSWORD_NOT_MATCH);
        }
        
        // 4. 验证短信验证码
        boolean isValidCode = smsService.validateResetPasswordCode(phone, smsCode, smsKey);
        if (!isValidCode) {
            throw new BusinessException(ErrorEnum.SMS_CODE_ERROR);
        }
        
        // 5. 查找用户（先查小程序用户，再查后台用户）
        User miniappUser = miniappUserService.getByPhone(phone);
        SysUser sysUser = userService.getByPhone(phone);
        
        if (miniappUser == null && sysUser == null) {
            throw new BusinessException(ErrorEnum.USER_NOT_FOUND);
        }
        
        // 6. 加密新密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        
        boolean updateSuccess = false;
        
        // 7. 更新密码（优先更新小程序用户）
        if (miniappUser != null) {
            miniappUser.setPassword(encodedPassword);
            miniappUser.setUpdatedAt(LocalDateTime.now());
            updateSuccess = miniappUserService.updateById(miniappUser);
            log.info("小程序用户密码重置成功，手机号: {}, 用户ID: {}", phone, miniappUser.getId());
        } else if (sysUser != null) {
            sysUser.setPassword(encodedPassword);
            sysUser.setUpdateTime(LocalDateTime.now());
            updateSuccess = userService.updateById(sysUser);
            log.info("后台用户密码重置成功，手机号: {}, 用户ID: {}", phone, sysUser.getId());
        }
        
        if (!updateSuccess) {
            log.error("密码重置失败，数据库更新失败，手机号: {}", phone);
            throw new BusinessException(ErrorEnum.SYSTEM_ERROR);
        }
        
        return true;
    }
}