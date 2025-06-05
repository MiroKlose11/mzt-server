package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 城市树形结构DTO
 */
@Data
@Schema(description = "城市树形结构")
public class CityTreeDTO {
    @Schema(description = "城市ID")
    private Integer id;
    
    @Schema(description = "城市名称")
    private String name;
    
    @Schema(description = "子城市列表")
    private List<CityTreeDTO> children;
} 