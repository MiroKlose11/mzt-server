package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体类
 */
@Data
@TableName("role")
public class Role {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 唯一标识，如 doctor、lecturer
     */
    private String code;
    
    /**
     * 角色名称（中文）
     */
    private String name;
    
    /**
     * 说明
     */
    private String description;
    
    /**
     * 排序值，越大越靠前
     */
    private Integer sort;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 