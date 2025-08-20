package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.ArticleContent;
import com.example.mzt_server.mapper.ArticleContentMapper;
import com.example.mzt_server.service.ArticleContentService;
import org.springframework.stereotype.Service;

/**
 * 文章内容表Service实现类
 * 实现文章内容的基础增删改查服务
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {
}