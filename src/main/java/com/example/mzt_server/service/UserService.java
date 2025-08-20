package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.entity.User;

/**
 * 小程序用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 根据openid获取用户
     * @param openid 微信openid
     * @return 用户信息
     */
    User getByOpenid(String openid);
    
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);
    
    /**
     * 根据手机号获取用户
     * @param phone 手机号
     * @return 用户信息
     */
    User getByPhone(String phone);
    
    /**
     * 验证密码
     * @param inputPassword 输入的密码
     * @param storedPassword 存储的密码
     * @return 是否验证成功
     */
    boolean verifyPassword(String inputPassword, String storedPassword);
}