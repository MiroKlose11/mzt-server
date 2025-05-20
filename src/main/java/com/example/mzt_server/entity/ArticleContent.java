package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 文章内容表实体类
 * 对应表：article_content
 */
@Data
@TableName("article_content")
public class ArticleContent implements Serializable {
    /** 文章ID */
    @TableId
    private Long articleId;
    /** 文章富文本内容 */
    private String content;
} 