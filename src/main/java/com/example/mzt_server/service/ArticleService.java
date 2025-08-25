package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.entity.Article;

/**
 * 文章主表Service接口
 * 提供文章的基础增删改查服务
 */
public interface ArticleService extends IService<Article> {
    
    /**
     * 增加文章浏览量
     * @param id 文章ID
     * @return 是否成功
     */
    boolean incrementViews(Long id);
}