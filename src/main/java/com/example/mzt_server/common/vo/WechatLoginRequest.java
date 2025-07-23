package com.example.mzt_server.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 微信小程序登录请求
 */
@Data
@Schema(description = "微信小程序登录请求")
public class WechatLoginRequest {

    /**
     * 微信小程序登录code
     */
    @NotBlank(message = "登录code不能为空")
    @Schema(description = "微信小程序登录code", required = true, example = "0f3f4d8c7b2a19e0")
    private String code;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "微信用户")
    private String nickName;

    /**
     * 用户头像URL
     */
    @Schema(description = "用户头像URL", example = "https://example.com/avatar.jpg")
    private String avatarUrl;

    /**
     * 手机号加密数据
     */
    @Schema(description = "手机号加密数据（微信小程序获取手机号时返回）", example = "CiyLU1Aw2KjvrjMdj...")
    private String encryptedData;

    /**
     * 加密算法的初始向量
     */
    @Schema(description = "加密算法的初始向量", example = "r7BXXKkLb8qrSNn05n0qiA==")
    private String iv;
}