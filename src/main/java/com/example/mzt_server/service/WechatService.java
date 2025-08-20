package com.example.mzt_server.service;

import java.util.Map;

/**
 * 微信服务接口
 */
public interface WechatService {
    
    /**
     * 通过code获取微信用户的openid
     * 
     * @param code 微信小程序登录code
     * @return openid
     */
    String getOpenidByCode(String code);
    
    /**
     * 通过code获取微信用户信息（包含openid和session_key）
     * 
     * @param code 微信小程序登录code
     * @return 包含openid和session_key的Map
     */
    Map<String, String> getWechatUserInfo(String code);
    
    /**
     * 解密微信小程序获取到的手机号
     * 
     * @param encryptedData 加密数据
     * @param iv            初始向量
     * @param sessionKey    会话密钥
     * @return 手机号
     */
    String decryptPhoneNumber(String encryptedData, String iv, String sessionKey);
}