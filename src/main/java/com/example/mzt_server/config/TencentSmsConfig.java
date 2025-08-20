package com.example.mzt_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tencent.sms")
public class TencentSmsConfig {
    
    /**
     * 腾讯云SecretId
     */
    private String secretId;
    
    /**
     * 腾讯云SecretKey
     */
    private String secretKey;
    
    /**
     * 地域
     */
    private String region = "ap-beijing";
    
    /**
     * 短信应用ID
     */
    private String appId;
    
    /**
     * 短信模板ID
     */
    private String templateId;
    
    /**
     * 短信签名
     */
    private String signName;
    
    /**
     * 限流配置
     */
    private RateLimit rateLimit = new RateLimit();
    
    /**
     * 限流配置
     */
    @Data
    public static class RateLimit {
        /**
         * 每分钟最多发送次数
         */
        private int perMinute = 1;
        
        /**
         * 每小时最多发送次数
         */
        private int perHour = 5;
        
        /**
         * 每天最多发送次数
         */
        private int perDay = 10;
    }
}