package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.vo.CaptchaInfo;
import com.example.mzt_server.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private Producer captchaProducer;
    
    /**
     * Redis前缀
     */
    private static final String CAPTCHA_PREFIX = "captcha:";
    
    /**
     * 验证码有效期（分钟）
     */
    private static final long CAPTCHA_EXPIRATION = 5;
    
    /**
     * 生成验证码
     *
     * @return 验证码信息
     */
    @Override
    public CaptchaInfo generateCaptcha() {
        // 生成验证码文本
        String text = captchaProducer.createText();
        
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        
        // 转换为Base64
        String base64 = imageToBase64(image);
        
        // 生成随机key
        String key = UUID.randomUUID().toString().replace("-", "");
        
        // 存入Redis，设置过期时间
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX + key, text, CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        
        // 构建返回对象
        CaptchaInfo captchaInfo = new CaptchaInfo();
        captchaInfo.setCaptchaKey(key);
        captchaInfo.setCaptchaBase64(base64);
        
        return captchaInfo;
    }
    
    /**
     * 验证验证码
     *
     * @param captchaKey 验证码key
     * @param captchaCode 验证码
     * @return 是否有效
     */
    @Override
    public boolean validateCaptcha(String captchaKey, String captchaCode) {
        String redisKey = CAPTCHA_PREFIX + captchaKey;
        String codeInRedis = redisTemplate.opsForValue().get(redisKey);
        
        if (codeInRedis == null) {
            return false;
        }
        
        boolean isValid = codeInRedis.equalsIgnoreCase(captchaCode);
        
        // 验证成功后删除
        if (isValid) {
            redisTemplate.delete(redisKey);
        }
        
        return isValid;
    }
    
    /**
     * 图片转Base64
     *
     * @param image 图片
     * @return Base64字符串
     */
    private String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            return "data:image/jpg;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("验证码图片转Base64失败", e);
        }
    }
}