package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.common.vo.RegisterRequest;
import com.example.mzt_server.common.vo.WechatLoginRequest;
import com.example.mzt_server.common.vo.MiniappLoginRequest;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 后台管理系统登录
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
     * 小程序登录（支持多种登录方式）
     *
     * @param request 小程序登录请求
     * @return 登录结果
     */
    LoginResult miniappLogin(MiniappLoginRequest request);
    
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
     * 用户注册
     *
     * @param request 注册请求
     * @return 登录结果
     */
    LoginResult register(RegisterRequest request);
    
    /**
     * 注销登录
     *
     * @param token 访问令牌
     * @return 是否成功
     */
    boolean logout(String token);
}