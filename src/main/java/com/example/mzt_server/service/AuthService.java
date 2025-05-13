package com.example.mzt_server.service;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证服务
 */
@Service
public class AuthService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     *
     * @param request 登录请求
     * @return 登录结果
     */
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
    public CaptchaInfo getCaptcha() {
        return captchaService.generateCaptcha();
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