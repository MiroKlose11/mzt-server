package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.entity.User;

/**
 * 小程序用户服务接口
 */
public interface IUserService extends IService<User> {
    
    /**
     * 根据openid获取用户
     * @param openid 微信openid
     * @return 用户信息
     */
    User getByOpenid(String openid);
}