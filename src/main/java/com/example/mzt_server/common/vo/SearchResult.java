package com.example.mzt_server.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 统一搜索结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "搜索结果项")
public class SearchResult {
    
    /**
     * 结果类型：article/member/course/organization/student
     */
    @Schema(description = "结果类型", example = "article")
    private String type;
    
    /**
     * 主键ID
     */
    @Schema(description = "主键ID", example = "1")
    private Object id;
    
    /**
     * 标题（显示名称）
     */
    @Schema(description = "标题", example = "如何选择适合自己的医美项目")
    private String title;
    
    /**
     * 描述（简介）
     */
    @Schema(description = "描述", example = "本文介绍如何根据自己的需求选择合适的医美项目")
    private String desc;
    
    /**
     * 额外字段（除id外的全部字段）
     */
    @Schema(description = "额外字段（除id外的全部字段）")
    private Map<String, Object> extra;
}

