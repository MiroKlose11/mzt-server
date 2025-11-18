package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
@TableName("course")
@Schema(description = "课程信息")
public class Course {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "课程ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    @Schema(description = "课程标题", example = "医美基础理论课程", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    
    @Schema(description = "课程简介", example = "本课程介绍医美基础理论知识")
    private String description;
    
    @Schema(description = "封面图片URL", example = "https://example.com/cover.jpg")
    private String coverUrl;
    
    @Schema(description = "讲师ID", example = "1")
    private Integer instructorId;
    
    @Schema(description = "所属机构ID", example = "1")
    private Integer organizationId;
    
    @Schema(description = "课程类型", example = "video", allowableValues = {"video", "article", "live", "other"})
    private String courseType;
    
    @Schema(description = "视频播放地址", example = "https://example.com/video.mp4")
    private String videoUrl;
    
    @Schema(description = "视频时长（秒）", example = "3600")
    private Integer videoDuration;
    
    @Schema(description = "价格", example = "99.99")
    private BigDecimal price;
    
    @Schema(description = "是否付费课程", example = "true")
    private Boolean isPaid;
    
    @Schema(description = "状态", example = "true")
    private Boolean status;
    
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 