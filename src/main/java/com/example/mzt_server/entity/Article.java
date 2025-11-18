package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章主表实体类
 * 对应表：article
 */
@Data
@TableName("article")
@Schema(description = "文章信息")
public class Article implements Serializable {
    /** 文章ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "文章ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    /** 文章标题 */
    @Schema(description = "文章标题", example = "如何选择适合自己的医美项目", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    
    /** 封面图地址 */
    @Schema(description = "封面图地址", example = "https://example.com/cover.jpg")
    private String coverImage;
    
    /** 简要描述 */
    @Schema(description = "简要描述", example = "本文介绍如何根据自己的需求选择合适的医美项目")
    private String description;
    
    /** 排序权重，值越大越靠前 */
    @Schema(description = "排序权重，值越大越靠前", example = "100")
    private Integer weight;
    
    /** 状态：0=草稿，1=待审核，2=已发布，3=已驳回 */
    @Schema(description = "状态", example = "2", allowableValues = {"0", "1", "2", "3"})
    private Integer status;
    
    /** 是否允许未登录用户查看：1=允许，0=不允许 */
    @Schema(description = "是否允许未登录用户查看", example = "1", allowableValues = {"0", "1"})
    private Integer isGuestVisible;
    
    /** 主栏目ID */
    @Schema(description = "主栏目ID", example = "1")
    private Long channelId;
    
    /** 作者ID（管理员或用户） */
    @Schema(description = "作者ID", example = "1")
    private Long authorId;
    
    /** 作者类型：0=管理员，1=用户 */
    @Schema(description = "作者类型", example = "0", allowableValues = {"0", "1"})
    private Integer authorType;
    
    /** 浏览量 */
    @Schema(description = "浏览量", example = "1000", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer views;
    
    /** 点赞数 */
    @Schema(description = "点赞数", example = "100", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer likes;
    
    /** 评论数 */
    @Schema(description = "评论数", example = "50", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer comments;
    
    /** 创建时间 */
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createtime;
    
    /** 更新时间 */
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatetime;
    
    /** 发布时间 */
    @Schema(description = "发布时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Date publishtime;
    
    /** 逻辑删除时间 */
    @Schema(description = "逻辑删除时间", hidden = true)
    private Date deletetime;
} 