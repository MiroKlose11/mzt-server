package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 机构实体类
 */
@Data
@TableName("organization")
public class Organization {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer typeId;
    private String address;
    private Integer cityId;
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 机构封面图URL
     */
    private String avatar;
    /**
     * 状态，1=启用，0=禁用
     */
    private Integer status;
    
    /**
     * 权重，越大越靠前
     */
    private Integer weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 