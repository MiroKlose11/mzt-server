package com.example.cms_mzt.controller;

import com.example.cms_mzt.common.Result;
import com.example.cms_mzt.entity.Banner;
import com.example.cms_mzt.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页Banner控制器
 */
@RestController
@RequestMapping("/index")
public class HomeBannerController {
    
    @Autowired
    private BannerService bannerService;
    
    /**
     * 获取顶部Banner轮播图
     * 
     * @return 顶部Banner轮播图列表
     */
    @GetMapping("/banners")
    public Result<List<Banner>> getTopBanners() {
        // 顶部Banner位置为1
        List<Banner> banners = bannerService.getBannersByPosition(1);
        return Result.success(banners);
    }
    
    /**
     * 获取平台介绍Banner轮播图
     * 
     * @return 平台介绍Banner轮播图列表
     */
    @GetMapping("/introduction-banners")
    public Result<List<Banner>> getIntroductionBanners() {
        // 平台介绍Banner位置为2
        List<Banner> banners = bannerService.getBannersByPosition(2);
        return Result.success(banners);
    }
} 