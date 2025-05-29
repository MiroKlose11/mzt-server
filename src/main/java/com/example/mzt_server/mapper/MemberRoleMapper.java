package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.MemberRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成员-角色关联Mapper接口
 */
@Mapper
public interface MemberRoleMapper extends BaseMapper<MemberRole> {
    
    /**
     * 批量插入成员角色关联
     * @param memberId 成员ID
     * @param roleIds 角色ID列表
     * @return 影响行数
     */
    int batchInsert(@Param("memberId") Integer memberId, @Param("roleIds") List<Integer> roleIds);
    
    /**
     * 根据成员ID删除关联
     * @param memberId 成员ID
     * @return 影响行数
     */
    int deleteByMemberId(@Param("memberId") Integer memberId);
} 