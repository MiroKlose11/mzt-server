package com.example.mzt_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云COS配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentCosConfig {
    /** 腾讯云SecretId */
    private String secretId;
    /** 腾讯云SecretKey */
    private String secretKey;
    /** 存储桶所在地域 */
    private String region;
    /** 存储桶名称 */
    private String bucketName;
    /** 存储桶访问域名 */
    private String baseUrl;
} 