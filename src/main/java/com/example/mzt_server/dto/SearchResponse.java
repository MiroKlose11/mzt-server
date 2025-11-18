package com.example.mzt_server.dto;

import com.example.mzt_server.common.vo.SearchResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索响应结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "搜索响应结果")
public class SearchResponse {
    
    /**
     * 搜索结果列表
     */
    @Schema(description = "搜索结果列表")
    private List<SearchResult> results;
    
    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "100")
    private Long total;
    
    /**
     * 当前页码
     */
    @Schema(description = "当前页码", example = "1")
    private Integer page;
    
    /**
     * 每页数量
     */
    @Schema(description = "每页数量", example = "10")
    private Integer size;
    
    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "10")
    private Long totalPages;
}

