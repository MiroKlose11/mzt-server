package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.RoleDTO;
import com.example.mzt_server.entity.Role;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService extends IService<Role> {
    
    /**
     * 获取角色列表
     * @return 角色列表
     */
    List<RoleDTO> listRoles();
    
    /**
     * 根据ID获取角色详情
     * @param id 角色ID
     * @return 角色详情
     */
    RoleDTO getRoleById(Integer id);
    
    /**
     * 新增角色
     * @param roleDTO 角色信息
     * @return 是否成功
     */
    boolean saveRole(RoleDTO roleDTO);
    
    /**
     * 更新角色
     * @param roleDTO 角色信息
     * @return 是否成功
     */
    boolean updateRole(RoleDTO roleDTO);
    
    /**
     * 删除角色
     * @param id 角色ID
     * @return 是否成功
     */
    boolean removeRole(Integer id);
} 