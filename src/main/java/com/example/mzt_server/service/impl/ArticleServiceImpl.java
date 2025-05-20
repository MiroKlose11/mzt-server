package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.Article;
import com.example.mzt_server.mapper.ArticleMapper;
import com.example.mzt_server.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章主表Service实现类
 * 实现文章的基础增删改查服务
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
} 