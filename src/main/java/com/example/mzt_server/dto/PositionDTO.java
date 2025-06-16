package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位数据传输对象
 */
@Data
@Schema(description = "岗位信息")
public class PositionDTO {
    @Schema(description = "岗位ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "岗位名称", required = true)
    private String name;

    @Schema(description = "岗位描述")
    private String description;

    @Schema(description = "排序值，越小越靠前")
    private Integer sort;

    @Schema(description = "状态：true=启用，false=禁用")
    private Boolean status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 