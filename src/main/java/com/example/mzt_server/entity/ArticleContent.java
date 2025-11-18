package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 文章内容表实体类
 * 对应表：article_content
 */
@Data
@TableName("article_content")
@Schema(description = "文章内容")
public class ArticleContent implements Serializable {
    /** 文章ID */
    @TableId
    @Schema(description = "文章ID", example = "1")
    private Long articleId;
    
    /** 文章富文本内容 */
    @Schema(description = "文章富文本内容，支持HTML格式", example = "<p>文章正文内容</p>")
    private String content;
} 