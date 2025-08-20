package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.CategoryItem;
import com.example.mzt_server.mapper.CategoryItemMapper;
import com.example.mzt_server.service.CategoryItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页分类条目服务实现类
 */
@Service
public class CategoryItemServiceImpl extends ServiceImpl<CategoryItemMapper, CategoryItem> implements CategoryItemService {
    @Override
    public List<CategoryItem> getItemsByCategoryType(String categoryType) {
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryItem::getCategoryType, categoryType)
                    .eq(CategoryItem::getStatus, 1)
                    .orderByAsc(CategoryItem::getSort);
        return this.list(queryWrapper);
    }
}