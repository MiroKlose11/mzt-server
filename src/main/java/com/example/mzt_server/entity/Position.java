package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位实体类
 */
@Data
@TableName("position")
public class Position {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer sort;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 