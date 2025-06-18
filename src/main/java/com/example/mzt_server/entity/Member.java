package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成员实体类
 */
@Data
@TableName("member")
public class Member {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 性别 0=未知 1=男 2=女
     */
    private Integer gender;
    
    /**
     * 机构ID，关联 organization 表
     */
    private Integer organizationId;
    
    /**
     * 备用机构名称（兜底用）
     */
    private String organization;
    
    /**
     * 职业所在地ID
     */
    private Integer cityId;
    
    /**
     * 个人简介
     */
    private String introduction;
    
    /**
     * 状态：1=启用 0=禁用
     */
    private Integer status;
    
    /**
     * 用户ID，关联用户表
     */
    private Integer userId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 