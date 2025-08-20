package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.config.WechatConfig;
import com.example.mzt_server.service.WechatService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信服务实现类
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatConfig wechatConfig;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

    /**
     * 微信小程序code2session接口地址
     */
    private static final String WECHAT_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 通过code获取微信用户信息（包含openid和session_key）
     * 
     * @param code 微信小程序登录code
     * @return 包含openid和session_key的Map
     */
    @Override
    public Map<String, String> getWechatUserInfo(String code) {
        try {
            // 构建请求URL
            String url = WECHAT_CODE2SESSION_URL +
                    "?appid=" + wechatConfig.getMiniapp().getAppId() +
                    "&secret=" + wechatConfig.getMiniapp().getSecret() +
                    "&js_code=" + code +
                    "&grant_type=authorization_code";

            log.info("调用微信code2session接口，code: {}", code);

            // 调用微信接口
            String response = restTemplate.getForObject(url, String.class);
            log.info("微信接口返回: {}", response);

            if (response == null) {
                throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
            }

            // 解析响应
            @SuppressWarnings("unchecked")
            Map<String, Object> result = gson.fromJson(response, Map.class);

            // 检查是否有错误
            if (result.containsKey("errcode")) {
                Integer errcode = ((Double) result.get("errcode")).intValue();
                if (errcode != 0) {
                    String errmsg = (String) result.get("errmsg");
                    log.error("微信登录失败，errcode: {}, errmsg: {}", errcode, errmsg);
                    throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
                }
            }

            // 获取openid和session_key
            String openid = (String) result.get("openid");
            String sessionKey = (String) result.get("session_key");
            String unionid = (String) result.get("unionid");
            
            if (openid == null || openid.trim().isEmpty()) {
                log.error("微信接口返回的openid为空");
                throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
            }

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("openid", openid);
            userInfo.put("session_key", sessionKey);
            if (unionid != null) {
                userInfo.put("unionid", unionid);
            }

            log.info("获取到微信用户信息: openid={}", openid);
            return userInfo;

        } catch (Exception e) {
            log.error("微信登录异常", e);
            throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
        }
    }

    /**
     * 兼容旧方法，只返回openid
     */
    @Override
    public String getOpenidByCode(String code) {
        Map<String, String> userInfo = getWechatUserInfo(code);
        return userInfo.get("openid");
    }

    /**
     * 解密微信小程序获取到的手机号
     * 
     * @param encryptedData 加密数据
     * @param iv            初始向量
     * @param sessionKey    会话密钥
     * @return 手机号
     */
    @Override
    public String decryptPhoneNumber(String encryptedData, String iv, String sessionKey) {
        try {
            if (!StringUtils.hasText(encryptedData) || !StringUtils.hasText(iv) || !StringUtils.hasText(sessionKey)) {
                throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
            }

            // Base64解码
            byte[] encryptedBytes = Base64.decodeBase64(encryptedData);
            byte[] ivBytes = Base64.decodeBase64(iv);
            byte[] sessionKeyBytes = Base64.decodeBase64(sessionKey);

            // AES解密
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

            log.info("解密后的数据: {}", decryptedData);

            // 解析JSON获取手机号
            @SuppressWarnings("unchecked")
            Map<String, Object> phoneInfo = gson.fromJson(decryptedData, Map.class);
            String phoneNumber = (String) phoneInfo.get("phoneNumber");

            if (!StringUtils.hasText(phoneNumber)) {
                log.error("解密后未找到手机号");
                throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
            }

            return phoneNumber;

        } catch (Exception e) {
            log.error("解密手机号失败", e);
            throw new BusinessException(ErrorEnum.WECHAT_LOGIN_ERROR);
        }
    }
}