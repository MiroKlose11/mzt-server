package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.entity.Banner;
import com.example.mzt_server.entity.CategoryItem;
import com.example.mzt_server.service.BannerService;
import com.example.mzt_server.service.CategoryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页统一接口控制器
 */
@Tag(name = "首页接口", description = "提供首页所需的所有接口")
@RestController
@RequestMapping("/index")
public class IndexController {
    
    @Autowired
    private CategoryItemService categoryItemService;
    
    @Autowired
    private BannerService bannerService;
    
    /**
     * 获取首页顶部Banner
     */
    @Operation(summary = "获取首页顶部Banner", description = "获取首页顶部轮播图数据")
    @GetMapping("/banner/top")
    public Result<List<Banner>> getTopBanners() {
        List<Banner> banners = bannerService.getBannersByPosition(1);
        return Result.success(banners);
    }
    
    /**
     * 获取平台介绍Banner
     */
    @Operation(summary = "获取平台介绍Banner", description = "获取平台介绍轮播图数据")
    @GetMapping("/banner/introduction")
    public Result<List<Banner>> getIntroductionBanners() {
        List<Banner> banners = bannerService.getBannersByPosition(2);
        return Result.success(banners);
    }
    
    /**
     * 获取分类列表
     */
    @Operation(summary = "获取分类列表", description = "获取指定类型的分类数据")
    @GetMapping("/category")
    public Result<List<CategoryItem>> getCategoryItems(
            @Parameter(description = "分类类型(service/platform/course/job)") 
            @RequestParam String type) {
        // 重定向到新的CategoryItemController
        return Result.success(categoryItemService.getItemsByCategoryType(type));
    }
    
    /**
     * 获取所有分类数据
     */
    @Operation(summary = "获取所有分类数据", description = "获取所有类型的分类数据")
    @GetMapping("/categories")
    public Result<Map<String, List<CategoryItem>>> getAllCategories() {
        Map<String, List<CategoryItem>> result = new HashMap<>();
        result.put("services", categoryItemService.getItemsByCategoryType("service"));
        result.put("platforms", categoryItemService.getItemsByCategoryType("platform"));
        result.put("courses", categoryItemService.getItemsByCategoryType("course"));
        result.put("jobs", categoryItemService.getItemsByCategoryType("job"));
        return Result.success(result);
    }
    
    /**
     * 获取首页所有数据（一次性）
     */
    @Operation(summary = "获取首页所有数据", description = "一次性获取首页需要的所有数据")
    @GetMapping("/all")
    public Result<Map<String, Object>> getAllData() {
        Map<String, Object> result = new HashMap<>();
        
        // 添加Banner数据
        result.put("topBanners", bannerService.getBannersByPosition(1));
        result.put("introductionBanners", bannerService.getBannersByPosition(2));
        
        // 添加分类数据
        result.put("services", categoryItemService.getItemsByCategoryType("service"));
        result.put("platforms", categoryItemService.getItemsByCategoryType("platform"));
        result.put("courses", categoryItemService.getItemsByCategoryType("course"));
        result.put("jobs", categoryItemService.getItemsByCategoryType("job"));
        
        return Result.success(result);
    }
} 