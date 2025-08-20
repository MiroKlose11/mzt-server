package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.common.vo.UserProfileForm;
import com.example.mzt_server.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getByUsername(String username);

    /**
     * 根据openid查询用户
     *
     * @param openid 微信openid
     * @return 用户信息
     */
    SysUser getByOpenid(String openid);

    /**
     * 验证密码
     *
     * @param rawPassword 原始密码
     * @param encodedPassword 编码后的密码
     * @return 是否有效
     */
    boolean verifyPassword(String rawPassword, String encodedPassword);

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<String> getUserRoles(Long userId);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    Map<String, Object> getUserInfo(Long userId);
    
    /**
     * 获取用户个人信息
     *
     * @param userId 用户ID
     * @return 用户个人信息
     */
    UserProfileForm getUserProfile(Long userId);
} 