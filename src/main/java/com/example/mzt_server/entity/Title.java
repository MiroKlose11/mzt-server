package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 头衔实体类
 */
@Data
@TableName("title")
public class Title {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 头衔名称，如主任医师
     */
    private String name;
    
    /**
     * 适用角色类型，可选，如 doctor
     */
    private String type;
    
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
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 