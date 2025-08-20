package com.example.mzt_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.common.Result;
import com.example.mzt_server.entity.Banner;
import com.example.mzt_server.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图管理控制器
 */
@Tag(name = "轮播图管理", description = "轮播图的增删改查接口")
@RestController
public class BannerController {

    @Autowired
    private BannerService bannerService;
    
    /**
     * 获取轮播图列表（用户端）
     */
    @Operation(summary = "获取轮播图列表", description = "前台获取轮播图列表，可按位置筛选")
    @GetMapping("/index/banner/list")
    public Result<List<Banner>> listForFrontend(
            @Parameter(description = "轮播图位置(1:顶部Banner, 2:平台介绍Banner)") 
            @RequestParam(required = false) Integer position) {
        
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        
        // 只查询已启用的
        queryWrapper.eq(Banner::getStatus, 1);
        
        // 如果位置参数存在，添加位置筛选条件
        if (position != null) {
            queryWrapper.eq(Banner::getPosition, position);
        }
        
        // 按排序字段排序
        queryWrapper.orderByAsc(Banner::getSort);
        
        List<Banner> list = bannerService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 添加轮播图（管理端）
     */
    @Operation(summary = "添加轮播图", description = "创建新的轮播图")
    @PostMapping("/homepage/banner")
    public Result<Banner> add(@RequestBody Banner banner) {
        bannerService.save(banner);
        return Result.success(banner);
    }

    /**
     * 删除轮播图（管理端）
     */
    @Operation(summary = "删除轮播图", description = "根据ID删除轮播图")
    @DeleteMapping("/homepage/banner/{id}")
    public Result<Boolean> delete(@Parameter(description = "轮播图ID") @PathVariable Long id) {
        boolean success = bannerService.removeById(id);
        return Result.success(success);
    }

    /**
     * 更新轮播图（管理端）
     */
    @Operation(summary = "更新轮播图", description = "更新轮播图信息")
    @PutMapping("/homepage/banner")
    public Result<Boolean> update(@RequestBody Banner banner) {
        boolean success = bannerService.updateById(banner);
        return Result.success(success);
    }

    /**
     * 获取轮播图详情（管理端）
     */
    @Operation(summary = "获取轮播图详情", description = "根据ID获取轮播图详情")
    @GetMapping("/homepage/banner/{id}")
    public Result<Banner> getById(@Parameter(description = "轮播图ID") @PathVariable Long id) {
        Banner banner = bannerService.getById(id);
        return Result.success(banner);
    }
    
    /**
     * 分页获取轮播图列表（管理端）
     */
    @Operation(summary = "分页获取轮播图列表", description = "管理端分页获取轮播图列表")
    @GetMapping("/homepage/banner/page")
    public Result<Page<Banner>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "轮播图位置") @RequestParam(required = false) Integer position) {
        
        Page<Banner> page = new Page<>(current, size);
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果位置参数存在，添加位置筛选条件
        if (position != null) {
            queryWrapper.eq(Banner::getPosition, position);
        }
        
        // 按ID排序
        queryWrapper.orderByDesc(Banner::getId);
        
        bannerService.page(page, queryWrapper);
        return Result.success(page);
    }
    
    /**
     * 获取所有轮播图列表（管理端）
     */
    @Operation(summary = "获取所有轮播图列表", description = "管理端获取所有轮播图列表，可按位置筛选")
    @GetMapping("/homepage/banner/list")
    public Result<List<Banner>> list(
            @Parameter(description = "轮播图位置") @RequestParam(required = false) Integer position) {
        
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果位置参数存在，添加位置筛选条件
        if (position != null) {
            queryWrapper.eq(Banner::getPosition, position);
        }
        
        // 按排序字段排序
        queryWrapper.orderByAsc(Banner::getSort);
        
        List<Banner> list = bannerService.list(queryWrapper);
        return Result.success(list);
    }
}