package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.common.vo.WechatLoginRequest;

/**
 * 认证服务接口
 */
public interface IAuthService {
    
    /**
     * 登录
     *
     * @param request 登录请求
     * @return 登录结果
     */
    LoginResult login(LoginRequest request);
    
    /**
     * 微信小程序登录
     *
     * @param request 微信登录请求
     * @return 登录结果
     */
    LoginResult wechatLogin(WechatLoginRequest request);
    
    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    LoginResult refreshToken(String refreshToken);
    
    /**
     * 获取验证码
     *
     * @return 验证码信息
     */
    CaptchaInfo getCaptcha();
    
    /**
     * 注销登录
     *
     * @param token 访问令牌
     * @return 是否成功
     */
    boolean logout(String token);
} 