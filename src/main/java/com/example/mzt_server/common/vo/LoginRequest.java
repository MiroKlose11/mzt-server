package com.example.mzt_server.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求
 */
@Data
@Schema(description = "登录请求")
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", required = true, example = "admin")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", required = true, example = "123456")
    private String password;

    /**
     * 验证码key
     */
    @NotBlank(message = "验证码key不能为空")
    @Schema(description = "验证码key", required = true, example = "8f7e6d5c4b3a2910")
    private String captchaKey;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", required = true, example = "a1b2")
    private String captchaCode;

    /**
     * 记住我
     */
    @Schema(description = "记住我", example = "false")
    private Boolean rememberMe;
} 