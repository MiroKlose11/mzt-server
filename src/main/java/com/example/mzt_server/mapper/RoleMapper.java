package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    /**
     * 根据成员ID查询角色列表
     * @param memberId 成员ID
     * @return 角色列表
     */
    List<Role> selectRolesByMemberId(@Param("memberId") Integer memberId);
} 