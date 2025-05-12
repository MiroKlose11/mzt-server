package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

/**
 * Banner轮播图数据访问层
 */
@Mapper
public interface BannerMapper extends BaseMapper<Banner> {
} 