package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.config.TencentSmsConfig;
import com.example.mzt_server.service.SmsService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 短信服务实现类
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TencentSmsConfig smsConfig;
    private final SecureRandom secureRandom = new SecureRandom();
    
    // 手机号正则表达式
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    public SmsServiceImpl(RedisTemplate<String, String> redisTemplate, TencentSmsConfig smsConfig) {
        this.redisTemplate = redisTemplate;
        this.smsConfig = smsConfig;
    }

    @Override
    public String sendLoginCode(String phone) {
        // 1. 验证手机号格式
        if (!isValidPhone(phone)) {
            throw new BusinessException(ErrorEnum.PHONE_FORMAT_ERROR);
        }
        
        // 2. 检查发送频率限制
        checkRateLimit(phone);
        
        // 3. 生成6位随机验证码
        String code = generateCode();
        
        // 4. 生成验证码key（使用UUID确保唯一性）
        String key = "sms:login:" + phone + ":" + UUID.randomUUID().toString();
        
        // 5. 将验证码存储到Redis，5分钟有效期
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        
        try {
            // 6. 调用腾讯云短信服务发送验证码
            sendSmsViaTencent(phone, code);
            
            // 7. 设置发送频率限制
            setRateLimit(phone);
            
            log.info("短信验证码发送成功，手机号: {}, key: {}", phone, key);
            return key;
            
        } catch (Exception e) {
            // 发送失败时删除已存储的验证码
            redisTemplate.delete(key);
            log.error("短信发送失败，手机号: {}, 错误: {}", phone, e.getMessage(), e);
            throw new BusinessException(ErrorEnum.SMS_SEND_FAILED);
        }
    }

    @Override
    public boolean validateLoginCode(String phone, String code, String key) {
        // 1. 参数验证
        if (!StringUtils.hasText(phone) || !StringUtils.hasText(code) || !StringUtils.hasText(key)) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        
        // 2. 验证手机号格式
        if (!isValidPhone(phone)) {
            throw new BusinessException(ErrorEnum.PHONE_FORMAT_ERROR);
        }
        
        // 3. 从Redis获取验证码
        String storedCode = redisTemplate.opsForValue().get(key);
        
        if (storedCode == null) {
            log.warn("验证码已过期或不存在，手机号: {}, key: {}", phone, key);
            throw new BusinessException(ErrorEnum.SMS_CODE_ERROR);
        }
        
        // 4. 验证码匹配
        boolean isValid = storedCode.equals(code);
        
        if (isValid) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            log.info("验证码验证成功，手机号: {}", phone);
        } else {
            log.warn("验证码验证失败，手机号: {}, 输入验证码: {}", phone, code);
            throw new BusinessException(ErrorEnum.SMS_CODE_ERROR);
        }
        
        return isValid;
    }
    
    /**
     * 生成6位随机验证码（使用安全随机数）
     */
    private String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(secureRandom.nextInt(10));
        }
        return code.toString();
    }
    
    /**
     * 验证手机号格式
     */
    private boolean isValidPhone(String phone) {
        return StringUtils.hasText(phone) && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * 检查发送频率限制
     */
    private void checkRateLimit(String phone) {
        // 检查每分钟限制
        String minuteKey = "sms:rate:minute:" + phone;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(minuteKey))) {
            String count = redisTemplate.opsForValue().get(minuteKey);
            if (count != null && Integer.parseInt(count) >= smsConfig.getRateLimit().getPerMinute()) {
                throw new BusinessException(ErrorEnum.SMS_TOO_FREQUENT);
            }
        }
        
        // 检查每小时限制
        String hourKey = "sms:rate:hour:" + phone;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(hourKey))) {
            String count = redisTemplate.opsForValue().get(hourKey);
            if (count != null && Integer.parseInt(count) >= smsConfig.getRateLimit().getPerHour()) {
                throw new BusinessException(ErrorEnum.SMS_TOO_FREQUENT);
            }
        }
        
        // 检查每天限制
        String dayKey = "sms:rate:day:" + phone;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(dayKey))) {
            String count = redisTemplate.opsForValue().get(dayKey);
            if (count != null && Integer.parseInt(count) >= smsConfig.getRateLimit().getPerDay()) {
                throw new BusinessException(ErrorEnum.SMS_TOO_FREQUENT);
            }
        }
    }
    
    /**
     * 设置发送频率限制
     */
    private void setRateLimit(String phone) {
        // 设置每分钟计数
        String minuteKey = "sms:rate:minute:" + phone;
        redisTemplate.opsForValue().increment(minuteKey);
        redisTemplate.expire(minuteKey, 1, TimeUnit.MINUTES);
        
        // 设置每小时计数
        String hourKey = "sms:rate:hour:" + phone;
        redisTemplate.opsForValue().increment(hourKey);
        redisTemplate.expire(hourKey, 1, TimeUnit.HOURS);
        
        // 设置每天计数
        String dayKey = "sms:rate:day:" + phone;
        redisTemplate.opsForValue().increment(dayKey);
        redisTemplate.expire(dayKey, 1, TimeUnit.DAYS);
    }
    
    /**
     * 通过腾讯云发送短信
     */
    private void sendSmsViaTencent(String phone, String code) throws TencentCloudSDKException {
        // 1. 实例化一个认证对象
        Credential cred = new Credential(smsConfig.getSecretId(), smsConfig.getSecretKey());
        
        // 2. 实例化一个http选项
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        
        // 3. 实例化一个client选项
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        
        // 4. 实例化要请求产品的client对象
        SmsClient client = new SmsClient(cred, smsConfig.getRegion(), clientProfile);
        
        // 5. 实例化一个请求对象
        SendSmsRequest req = new SendSmsRequest();
        
        // 6. 设置短信应用ID
        req.setSmsSdkAppId(smsConfig.getAppId());
        
        // 7. 设置签名
        req.setSignName(smsConfig.getSignName());
        
        // 8. 设置模板ID
        req.setTemplateId(smsConfig.getTemplateId());
        
        // 9. 设置手机号
        String[] phoneNumbers = {"+86" + phone};
        req.setPhoneNumberSet(phoneNumbers);
        
        // 10. 设置模板参数（验证码）
        String[] templateParams = {code};
        req.setTemplateParamSet(templateParams);
        
        // 11. 发送短信
        SendSmsResponse resp = client.SendSms(req);
        
        // 12. 检查发送结果
        if (resp.getSendStatusSet() != null && resp.getSendStatusSet().length > 0) {
            String sendStatus = resp.getSendStatusSet()[0].getCode();
            if (!"Ok".equals(sendStatus)) {
                log.error("腾讯云短信发送失败，手机号: {}, 错误码: {}, 错误信息: {}", 
                    phone, sendStatus, resp.getSendStatusSet()[0].getMessage());
                throw new TencentCloudSDKException("短信发送失败: " + resp.getSendStatusSet()[0].getMessage());
            }
        }
        
        log.info("腾讯云短信发送成功，手机号: {}, RequestId: {}", phone, resp.getRequestId());
    }
}