package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.User;
import com.example.mzt_server.mapper.UserMapper;
import com.example.mzt_server.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 小程序用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getByOpenid(String openid) {
        if (!StringUtils.hasText(openid)) {
            return null;
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        return getOne(wrapper);
    }

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public User getByPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return null;
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return getOne(wrapper);
    }

    @Override
    public boolean verifyPassword(String inputPassword, String storedPassword) {
        if (!StringUtils.hasText(inputPassword) || !StringUtils.hasText(storedPassword)) {
            return false;
        }
        return passwordEncoder.matches(inputPassword, storedPassword);
    }
}