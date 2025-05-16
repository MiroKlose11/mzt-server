package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.CaptchaInfo;

/**
 * 验证码服务接口
 */
public interface ICaptchaService {
    
    /**
     * 生成验证码
     *
     * @return 验证码信息
     */
    CaptchaInfo generateCaptcha();
    
    /**
     * 验证验证码
     *
     * @param captchaKey 验证码key
     * @param captchaCode 验证码
     * @return 是否有效
     */
    boolean validateCaptcha(String captchaKey, String captchaCode);
} 