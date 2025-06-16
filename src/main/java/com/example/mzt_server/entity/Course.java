package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
@TableName("course")
public class Course {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String coverUrl;
    private Integer instructorId;
    private Integer organizationId;
    private String courseType;
    private String videoUrl;
    private Integer videoDuration;
    private BigDecimal price;
    private Boolean isPaid;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 