package com.example.cms_mzt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cms_mzt.entity.Banner;
import com.example.cms_mzt.mapper.BannerMapper;
import com.example.cms_mzt.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Banner轮播图服务实现类
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    
    /**
     * 根据位置获取轮播图列表
     *
     * @param position 轮播图位置 (1:顶部Banner, 2:平台介绍Banner)
     * @return 轮播图列表
     */
    @Override
    public List<Banner> getBannersByPosition(Integer position) {
        // 构建查询条件
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        // 根据位置查询
        queryWrapper.eq(Banner::getPosition, position);
        // 只查询已启用的Banner
        queryWrapper.eq(Banner::getStatus, 1);
        // 按排序字段排序
        queryWrapper.orderByAsc(Banner::getSort);
        
        // 返回查询结果
        return this.list(queryWrapper);
    }
} 