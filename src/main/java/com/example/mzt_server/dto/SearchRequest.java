package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 搜索请求参数
 */
@Data
@Schema(description = "搜索请求参数")
public class SearchRequest {
    
    /**
     * 搜索关键词
     */
    @NotBlank(message = "搜索关键词不能为空")
    @Schema(description = "搜索关键词", example = "医美", requiredMode = Schema.RequiredMode.REQUIRED)
    private String keyword;
    
    /**
     * 搜索类型数组，如 ["article", "member", "course"]
     * 不传则搜索全部类型
     */
    @Schema(description = "搜索类型数组", 
            example = "[\"article\", \"member\", \"course\"]",
            allowableValues = {"article", "member", "course", "organization", "student"})
    private List<String> types;
    
    /**
     * 页码，从1开始
     */
    @Min(value = 1, message = "页码必须大于0")
    @Schema(description = "页码", example = "1", defaultValue = "1")
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量必须大于0")
    @Schema(description = "每页数量", example = "10", defaultValue = "10")
    private Integer size = 10;
}

