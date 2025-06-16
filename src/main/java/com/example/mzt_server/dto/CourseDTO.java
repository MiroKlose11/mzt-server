package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程数据传输对象
 */
@Data
@Schema(description = "课程信息")
public class CourseDTO {
    @Schema(description = "课程ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "课程标题", required = true)
    private String title;

    @Schema(description = "课程简介")
    private String description;

    @Schema(description = "封面图片URL")
    private String coverUrl;

    @Schema(description = "讲师ID")
    private Integer instructorId;
    
    @Schema(description = "讲师姓名", accessMode = Schema.AccessMode.READ_ONLY)
    private String instructorName;

    @Schema(description = "所属机构ID")
    private Integer organizationId;
    
    @Schema(description = "所属机构名称", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "课程类型：video=视频课程，article=文章课程，live=直播课程，other=其他")
    private String courseType;

    @Schema(description = "视频播放地址")
    private String videoUrl;

    @Schema(description = "视频时长（秒）")
    private Integer videoDuration;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "是否付费课程")
    private Boolean isPaid;

    @Schema(description = "状态：true=上架，false=下架")
    private Boolean status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 