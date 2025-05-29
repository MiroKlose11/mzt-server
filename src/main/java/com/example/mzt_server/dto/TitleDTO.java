package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 头衔数据传输对象
 */
@Data
@Schema(description = "头衔信息")
public class TitleDTO {
    
    @Schema(description = "头衔ID")
    private Integer id;
    
    @NotBlank(message = "头衔名称不能为空")
    @Schema(description = "头衔名称，如主任医师", required = true)
    private String name;
    
    @Schema(description = "适用角色类型，可选，如 doctor")
    private String type;
    
    @Schema(description = "说明")
    private String description;
    
    @Schema(description = "排序值，越大越靠前")
    private Integer sort;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 