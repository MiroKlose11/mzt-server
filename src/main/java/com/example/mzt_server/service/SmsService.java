package com.example.mzt_server.service;

/**
 * 短信服务接口
 */
public interface SmsService {
    
    /**
     * 发送登录验证码
     * 
     * @param phone 手机号
     * @return 验证码key
     */
    String sendLoginCode(String phone);
    
    /**
     * 验证登录验证码
     * 
     * @param phone 手机号
     * @param code 验证码
     * @param key 验证码key
     * @return 是否验证成功
     */
    boolean validateLoginCode(String phone, String code, String key);
} 