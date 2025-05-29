package com.example.mzt_server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.MemberDTO;
import com.example.mzt_server.entity.Member;

/**
 * 成员服务接口
 */
public interface MemberService extends IService<Member> {
    
    /**
     * 分页查询成员列表
     * @param current 当前页
     * @param size 每页大小
     * @param name 姓名（可选）
     * @param gender 性别（可选）
     * @param cityId 城市ID（可选）
     * @param roleId 角色ID（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    IPage<MemberDTO> pageMember(long current, long size, String name, Integer gender, 
                              Integer cityId, Integer roleId, Integer status);
    
    /**
     * 根据ID获取成员详情
     * @param id 成员ID
     * @return 成员详情
     */
    MemberDTO getMemberById(Integer id);
    
    /**
     * 新增成员
     * @param memberDTO 成员信息
     * @return 是否成功
     */
    boolean saveMember(MemberDTO memberDTO);
    
    /**
     * 更新成员
     * @param memberDTO 成员信息
     * @return 是否成功
     */
    boolean updateMember(MemberDTO memberDTO);
    
    /**
     * 删除成员
     * @param id 成员ID
     * @return 是否成功
     */
    boolean removeMember(Integer id);
} 