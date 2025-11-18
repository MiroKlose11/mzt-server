package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体类
 */
@Data
@TableName("role")
@Schema(description = "角色信息")
public class Role {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "角色ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    /**
     * 唯一标识，如 doctor、lecturer
     */
    @Schema(description = "唯一标识", example = "doctor", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
    
    /**
     * 角色名称（中文）
     */
    @Schema(description = "角色名称（中文）", example = "医生", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    /**
     * 说明
     */
    @Schema(description = "说明", example = "医疗从业人员")
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
} 