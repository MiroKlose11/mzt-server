package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.common.vo.RegisterRequest;
import com.example.mzt_server.common.vo.WechatLoginRequest;
import com.example.mzt_server.common.vo.MiniappLoginRequest;
import com.example.mzt_server.service.AuthService;
import com.example.mzt_server.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SmsService smsService;

    /**
     * 后台管理系统登录
     */
    @Operation(summary = "后台管理系统登录", description = "后台管理系统用户登录接口")
    @PostMapping("/login")
    public Result<LoginResult> login(@Validated @RequestBody LoginRequest request) {
        LoginResult result = authService.login(request);
        return Result.success(result);
    }

    /**
     * 微信小程序登录
     */
    @Operation(summary = "微信小程序登录", description = "微信小程序登录接口")
    @PostMapping("/wechat-login")
    public Result<LoginResult> wechatLogin(@Validated @RequestBody WechatLoginRequest request) {
        LoginResult result = authService.wechatLogin(request);
        return Result.success(result);
    }

    /**
     * 小程序登录（支持多种登录方式）
     */
    @Operation(summary = "小程序登录", description = "小程序登录接口，支持微信登录、账号密码登录、手机验证码登录")
    @PostMapping("/miniapp-login")
    public Result<LoginResult> miniappLogin(@Validated @RequestBody MiniappLoginRequest request) {
        LoginResult result = authService.miniappLogin(request);
        return Result.success(result);
    }

    /**
     * 手机号快速注册
     */
    @Operation(summary = "手机号快速注册", description = "使用手机号和短信验证码快速注册")
    @PostMapping("/register")
    public Result<LoginResult> register(@Validated @RequestBody RegisterRequest request) {
        LoginResult result = authService.register(request);
        return Result.success(result);
    }

    /**
     * 发送短信验证码
     */
    @Operation(summary = "发送短信验证码", description = "发送登录短信验证码")
    @PostMapping("/send-sms")
    public Result<String> sendSmsCode(@RequestParam String phone) {
        String key = smsService.sendLoginCode(phone);
        return Result.success(key);
    }

    /**
     * 刷新令牌
     */
    @Operation(summary = "刷新令牌", description = "刷新访问令牌")
    @PostMapping("/refresh-token")
    public Result<LoginResult> refreshToken(@RequestParam String refreshToken) {
        LoginResult result = authService.refreshToken(refreshToken);
        return Result.success(result);
    }

    /**
     * 获取验证码
     */
    @Operation(summary = "获取验证码", description = "获取登录验证码")
    @GetMapping("/captcha")
    public Result<CaptchaInfo> getCaptcha() {
        CaptchaInfo captcha = authService.getCaptcha();
        return Result.success(captcha);
    }

    /**
     * 注销登录
     */
    @Operation(summary = "注销登录", description = "用户注销登录接口")
    @DeleteMapping("/logout")
    public Result<Object> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success(null);
    }
}