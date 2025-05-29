package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.MemberTitle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成员-头衔关联Mapper接口
 */
@Mapper
public interface MemberTitleMapper extends BaseMapper<MemberTitle> {
    
    /**
     * 批量插入成员头衔关联
     * @param memberId 成员ID
     * @param titleIds 头衔ID列表
     * @return 影响行数
     */
    int batchInsert(@Param("memberId") Integer memberId, @Param("titleIds") List<Integer> titleIds);
    
    /**
     * 根据成员ID删除关联
     * @param memberId 成员ID
     * @return 影响行数
     */
    int deleteByMemberId(@Param("memberId") Integer memberId);
} 