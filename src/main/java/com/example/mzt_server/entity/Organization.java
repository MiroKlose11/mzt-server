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
    private String phone;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 