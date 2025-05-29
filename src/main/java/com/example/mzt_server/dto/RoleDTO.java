package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 角色数据传输对象
 */
@Data
@Schema(description = "角色信息")
public class RoleDTO {
    
    @Schema(description = "角色ID")
    private Integer id;
    
    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "唯一标识，如 doctor、lecturer", required = true)
    private String code;
    
    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称（中文）", required = true)
    private String name;
    
    @Schema(description = "说明")
    private String description;
    
    @Schema(description = "排序值，越大越靠前")
    private Integer sort;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
} 