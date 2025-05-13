package com.example.mzt_server.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置
 */
@Configuration
public class KaptchaConfig {

    /**
     * 配置验证码生成器
     */
    @Bean
    public Producer captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        
        // 验证码宽度
        properties.setProperty("kaptcha.image.width", "150");
        // 验证码高度
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");
        // 字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 背景颜色渐变开始
        properties.setProperty("kaptcha.background.clear.from", "white");
        // 背景颜色渐变结束
        properties.setProperty("kaptcha.background.clear.to", "white");
        // 噪点
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        
        return kaptcha;
    }
} 