package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 头衔Mapper接口
 */
@Mapper
public interface TitleMapper extends BaseMapper<Title> {
    
    /**
     * 根据成员ID查询头衔列表
     * @param memberId 成员ID
     * @return 头衔列表
     */
    List<Title> selectTitlesByMemberId(@Param("memberId") Integer memberId);
    
    /**
     * 根据类型查询头衔列表
     * @param type 类型
     * @return 头衔列表
     */
    List<Title> selectTitlesByType(@Param("type") String type);
} 