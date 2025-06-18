package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@TableName("student")
public class Student {
    /**
     * 主键
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
     * 性别：0=未知，1=男，2=女
     */
    private Integer gender;
    
    /**
     * 个性签名
     */
    private String signature;
    
    /**
     * 所属机构ID
     */
    private Integer organizationId;
    
    /**
     * 用户ID，关联用户表
     */
    private Integer userId;
    
    /**
     * 状态：1=启用，0=禁用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 