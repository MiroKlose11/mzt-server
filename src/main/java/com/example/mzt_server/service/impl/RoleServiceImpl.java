package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.RoleDTO;
import com.example.mzt_server.entity.MemberRole;
import com.example.mzt_server.entity.Role;
import com.example.mzt_server.mapper.MemberRoleMapper;
import com.example.mzt_server.mapper.RoleMapper;
import com.example.mzt_server.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
    @Autowired
    private MemberRoleMapper memberRoleMapper;
    
    @Override
    public List<RoleDTO> listRoles() {
        // 按照排序字段降序排列
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Role::getSort).orderByAsc(Role::getId);
        
        List<Role> roles = list(queryWrapper);
        
        // 转换为DTO
        return roles.stream().map(role -> {
            RoleDTO dto = new RoleDTO();
            BeanUtils.copyProperties(role, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public RoleDTO getRoleById(Integer id) {
        Role role = getById(id);
        if (role == null) {
            return null;
        }
        
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }
    
    @Override
    public boolean saveRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setCreatedAt(LocalDateTime.now());
        
        // 设置默认排序值
        if (role.getSort() == null) {
            role.setSort(0);
        }
        
        return save(role);
    }
    
    @Override
    public boolean updateRole(RoleDTO roleDTO) {
        Role role = getById(roleDTO.getId());
        if (role == null) {
            return false;
        }
        
        BeanUtils.copyProperties(roleDTO, role);
        return updateById(role);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeRole(Integer id) {
        // 删除角色与成员的关联
        LambdaQueryWrapper<MemberRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberRole::getRoleId, id);
        memberRoleMapper.delete(queryWrapper);
        
        // 删除角色
        return removeById(id);
    }
} 