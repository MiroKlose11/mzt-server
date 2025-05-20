package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章主表实体类
 * 对应表：article
 */
@Data
@TableName("article")
public class Article implements Serializable {
    /** 文章ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 文章标题 */
    private String title;
    /** 封面图地址 */
    private String coverImage;
    /** 简要描述 */
    private String description;
    /** 排序权重，值越大越靠前 */
    private Integer weight;
    /** 状态：0=草稿，1=待审核，2=已发布，3=已驳回 */
    private Integer status;
    /** 是否允许未登录用户查看：1=允许，0=不允许 */
    private Integer isGuestVisible;
    /** 主栏目ID */
    private Long channelId;
    /** 作者ID（管理员或用户） */
    private Long authorId;
    /** 作者类型：0=管理员，1=用户 */
    private Integer authorType;
    /** 浏览量 */
    private Integer views;
    /** 点赞数 */
    private Integer likes;
    /** 评论数 */
    private Integer comments;
    /** 创建时间 */
    private Date createtime;
    /** 更新时间 */
    private Date updatetime;
    /** 发布时间 */
    private Date publishtime;
    /** 逻辑删除时间 */
    private Date deletetime;
} 