package com.example.mzt_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    /**
     * 微信公众号配置
     */
    private Mp mp = new Mp();

    /**
     * 微信小程序配置
     */
    private Miniapp miniapp = new Miniapp();

    @Data
    public static class Mp {
        /**
         * 微信公众号AppId
         */
        private String appId;

        /**
         * 微信公众号AppSecret
         */
        private String secret;
    }

    @Data
    public static class Miniapp {
        /**
         * 微信小程序AppId
         */
        private String appId;

        /**
         * 微信小程序AppSecret
         */
        private String secret;
    }
}