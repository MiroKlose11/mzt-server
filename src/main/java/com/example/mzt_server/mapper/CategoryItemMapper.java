package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.CategoryItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页分类条目数据访问层
 */
@Mapper
public interface CategoryItemMapper extends BaseMapper<CategoryItem> {
} 