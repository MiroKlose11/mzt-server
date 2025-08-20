package com.example.mzt_server.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 小程序登录请求（支持多种登录方式）
 */
@Data
@Schema(description = "小程序登录请求")
public class MiniappLoginRequest {

    /**
     * 登录类型：wechat=微信登录，password=账号密码登录，sms=手机验证码登录
     */
    @NotBlank(message = "登录类型不能为空")
    @Schema(description = "登录类型", required = true, example = "wechat", allowableValues = {"wechat", "password", "sms"})
    private String loginType;

    // ========== 微信登录参数 ==========
    /**
     * 微信小程序登录code（微信登录时必填）
     */
    @Schema(description = "微信小程序登录code（微信登录时必填）", example = "0f3f4d8c7b2a19e0")
    private String code;

    /**
     * 用户昵称（微信登录时可选）
     */
    @Schema(description = "用户昵称（微信登录时可选）", example = "微信用户")
    private String nickName;

    /**
     * 用户头像URL（微信登录时可选）
     */
    @Schema(description = "用户头像URL（微信登录时可选）", example = "https://example.com/avatar.jpg")
    private String avatarUrl;

    /**
     * 手机号加密数据（微信登录时可选）
     */
    @Schema(description = "手机号加密数据（微信登录时可选）", example = "CiyLU1Aw2KjvrjMdj...")
    private String encryptedData;

    /**
     * 加密算法的初始向量（微信登录时可选）
     */
    @Schema(description = "加密算法的初始向量（微信登录时可选）", example = "r7BXXKkLb8qrSNn05n0qiA==")
    private String iv;

    // ========== 账号密码登录参数 ==========
    /**
     * 用户名（账号密码登录时必填）
     */
    @Schema(description = "用户名（账号密码登录时必填）", example = "user123")
    private String username;

    /**
     * 密码（账号密码登录时必填）
     */
    @Schema(description = "密码（账号密码登录时必填）", example = "123456")
    private String password;

    // ========== 手机验证码登录参数 ==========
    /**
     * 手机号（手机验证码登录时必填）
     */
    @Schema(description = "手机号（手机验证码登录时必填）", example = "13800138000")
    private String phone;

    /**
     * 验证码（手机验证码登录时必填）
     */
    @Schema(description = "验证码（手机验证码登录时必填）", example = "123456")
    private String smsCode;

    /**
     * 验证码key（手机验证码登录时必填）
     */
    @Schema(description = "验证码key（手机验证码登录时必填）", example = "sms_123456789")
    private String smsKey;
} 