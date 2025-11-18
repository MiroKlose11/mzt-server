package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位实体类
 */
@Data
@TableName("position")
@Schema(description = "岗位信息")
public class Position {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "岗位ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    @Schema(description = "岗位名称", example = "皮肤管理师", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "岗位描述", example = "负责皮肤管理相关工作")
    private String description;
    
    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sort;
    
    @Schema(description = "状态", example = "true")
    private Boolean status;
    
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 