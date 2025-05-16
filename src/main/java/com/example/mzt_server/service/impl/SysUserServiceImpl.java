package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.mapper.SysUserMapper;
import com.example.mzt_server.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return getOne(wrapper);
    }

    /**
     * 验证密码
     *
     * @param rawPassword 原始密码
     * @param encodedPassword 编码后的密码
     * @return 是否有效
     */
    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<String> getUserRoles(Long userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<String> getUserPermissions(Long userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public Map<String, Object> getUserInfo(Long userId) {
        // 查询用户基本信息
        SysUser user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 查询用户角色和权限
        List<String> roles = getUserRoles(userId);
        List<String> permissions = getUserPermissions(userId);

        // 构建返回数据
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("roles", roles);
        userInfo.put("permissions", permissions);

        return userInfo;
    }
} 