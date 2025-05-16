package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.entity.CategoryItem;

import java.util.List;

/**
 * 分类条目服务接口
 */
public interface ICategoryItemService extends IService<CategoryItem> {
    
    /**
     * 根据分类类型获取条目列表
     * 
     * @param type 分类类型
     * @return 条目列表
     */
    List<CategoryItem> getItemsByCategoryType(String type);
} 