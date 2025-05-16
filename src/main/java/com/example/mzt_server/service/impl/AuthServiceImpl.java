package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.service.ICaptchaService;
import com.example.mzt_server.service.IAuthService;
import com.example.mzt_server.service.ISysUserService;
import com.example.mzt_server.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
} 