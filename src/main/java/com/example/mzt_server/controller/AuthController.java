package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.common.vo.LoginRequest;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.service.AuthService;
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

    /**
     * 登录
     */
    @Operation(summary = "登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<LoginResult> login(@Validated @RequestBody LoginRequest request) {
        LoginResult result = authService.login(request);
        return Result.success(result);
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