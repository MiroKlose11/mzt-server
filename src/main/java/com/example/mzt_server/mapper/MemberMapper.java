package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 成员Mapper接口
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    
    /**
     * 分页查询成员列表
     * @param page 分页参数
     * @param name 姓名（可选）
     * @param gender 性别（可选）
     * @param cityId 城市ID（可选）
     * @param roleId 角色ID（可选）
     * @param status 状态（可选）
     * @param userId 用户ID（可选）
     * @return 分页结果
     */
    IPage<Member> selectMemberPage(Page<Member> page, 
                                  @Param("name") String name,
                                  @Param("gender") Integer gender,
                                  @Param("cityId") Integer cityId,
                                  @Param("roleId") Integer roleId,
                                  @Param("status") Integer status,
                                  @Param("userId") Integer userId);
} 