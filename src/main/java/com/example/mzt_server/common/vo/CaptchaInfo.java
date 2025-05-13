package com.example.mzt_server.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 验证码信息
 */
@Data
@Schema(description = "验证码信息")
public class CaptchaInfo {

    /**
     * 验证码key
     */
    @Schema(description = "验证码key", example = "8f7e6d5c4b3a2910")
    private String captchaKey;

    /**
     * 验证码Base64图片
     */
    @Schema(description = "验证码Base64图片", example = "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD...")
    private String captchaBase64;
} 