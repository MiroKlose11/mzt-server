package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.entity.Banner;

import java.util.List;

/**
 * 轮播图服务接口
 */
public interface BannerService extends IService<Banner> {
    
    /**
     * 根据位置获取轮播图列表
     * 
     * @param position 轮播图位置(1:顶部Banner, 2:平台介绍Banner)
     * @return 轮播图列表
     */
    List<Banner> getBannersByPosition(Integer position);
}