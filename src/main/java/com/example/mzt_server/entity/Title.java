package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 头衔实体类
 */
@Data
@TableName("title")
@Schema(description = "头衔信息")
public class Title {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "头衔ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    /**
     * 头衔名称，如主任医师
     */
    @Schema(description = "头衔名称", example = "主任医师", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    /**
     * 适用角色类型，可选，如 doctor
     */
    @Schema(description = "适用角色类型", example = "doctor")
    private String type;
    
    /**
     * 说明
     */
    @Schema(description = "说明", example = "高级医师职称")
    private String description;
    
    /**
     * 排序值，越大越靠前
     */
    @Schema(description = "排序值，越大越靠前", example = "100")
    private Integer sort;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 