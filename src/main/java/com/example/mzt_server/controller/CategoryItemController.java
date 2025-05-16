package com.example.mzt_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.common.Result;
import com.example.mzt_server.entity.CategoryItem;
import com.example.mzt_server.service.ICategoryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 分类条目管理控制器
 */
@Tag(name = "分类条目管理", description = "分类条目的增删改查接口")
@RestController
public class CategoryItemController {

    @Autowired
    private ICategoryItemService categoryItemService;

    /**
     * 获取分类条目列表（用户端）
     */
    @Operation(summary = "获取分类条目列表", description = "前台获取分类条目列表，可按类型筛选")
    @GetMapping("/index/category-item")
    public Result<List<CategoryItem>> listForFrontend(
            @Parameter(description = "分类类型(service/platform/course/job)") 
            @RequestParam(required = true) String type) {
        
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        
        // 只查询已启用的
        queryWrapper.eq(CategoryItem::getStatus, 1);
        
        // 添加类型筛选条件
        queryWrapper.eq(CategoryItem::getCategoryType, type);
        
        // 按排序字段排序
        queryWrapper.orderByAsc(CategoryItem::getSort);
        
        List<CategoryItem> list = categoryItemService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 添加分类条目（管理端）
     */
    @Operation(summary = "添加分类条目", description = "创建新的分类条目")
    @PostMapping("/homepage/category-item")
    public Result<CategoryItem> add(@RequestBody CategoryItem categoryItem) {
        categoryItemService.save(categoryItem);
        return Result.success(categoryItem);
    }

    /**
     * 删除分类条目（管理端）
     */
    @Operation(summary = "删除分类条目", description = "根据ID删除分类条目")
    @DeleteMapping("/homepage/category-item/{id}")
    public Result<Boolean> delete(@Parameter(description = "分类条目ID") @PathVariable Long id) {
        boolean success = categoryItemService.removeById(id);
        return Result.success(success);
    }

    /**
     * 更新分类条目（管理端）
     */
    @Operation(summary = "更新分类条目", description = "更新分类条目信息")
    @PutMapping("/homepage/category-item")
    public Result<Boolean> update(@RequestBody CategoryItem categoryItem) {
        boolean success = categoryItemService.updateById(categoryItem);
        return Result.success(success);
    }

    /**
     * 获取分类条目详情（管理端）
     */
    @Operation(summary = "获取分类条目详情", description = "根据ID获取分类条目详情")
    @GetMapping("/homepage/category-item/{id}")
    public Result<CategoryItem> getById(@Parameter(description = "分类条目ID") @PathVariable Long id) {
        CategoryItem categoryItem = categoryItemService.getById(id);
        return Result.success(categoryItem);
    }
    
    /**
     * 分页获取分类条目列表（管理端）
     */
    @Operation(summary = "分页获取分类条目列表", description = "管理端分页获取分类条目列表")
    @GetMapping("/homepage/category-item/page")
    public Result<Page<CategoryItem>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "分类类型") @RequestParam(required = false) String type) {
        
        Page<CategoryItem> page = new Page<>(current, size);
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果类型参数存在，添加类型筛选条件
        if (type != null && !type.isEmpty()) {
            queryWrapper.eq(CategoryItem::getCategoryType, type);
        }
        
        // 按排序字段排序
        queryWrapper.orderByAsc(CategoryItem::getSort);
        
        categoryItemService.page(page, queryWrapper);
        return Result.success(page);
    }
    
    /**
     * 获取所有分类条目列表（管理端）
     */
    @Operation(summary = "获取所有分类条目列表", description = "管理端获取所有分类条目列表，可按类型筛选")
    @GetMapping("/homepage/category-item/list")
    public Result<List<CategoryItem>> list(
            @Parameter(description = "分类类型") @RequestParam(required = false) String type) {
        
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果类型参数存在，添加类型筛选条件
        if (type != null && !type.isEmpty()) {
            queryWrapper.eq(CategoryItem::getCategoryType, type);
        }
        
        // 按排序字段排序
        queryWrapper.orderByAsc(CategoryItem::getSort);
        
        List<CategoryItem> list = categoryItemService.list(queryWrapper);
        return Result.success(list);
    }
    
    /**
     * 获取所有分类类型列表（管理端）
     */
    @Operation(summary = "获取所有分类类型", description = "获取系统中所有的分类类型")
    @GetMapping("/homepage/category-item/types")
    public Result<List<String>> getAllTypes() {
        // 预定义的分类类型
        List<String> types = Arrays.asList("service", "platform", "course", "job");
        return Result.success(types);
    }
} 