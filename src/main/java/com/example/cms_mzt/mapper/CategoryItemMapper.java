package com.example.cms_mzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cms_mzt.entity.CategoryItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页分类条目数据访问层
 */
@Mapper
public interface CategoryItemMapper extends BaseMapper<CategoryItem> {
} 