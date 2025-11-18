package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.SearchRequest;
import com.example.mzt_server.dto.SearchResponse;
import com.example.mzt_server.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 搜索控制器
 * 提供全局搜索功能，支持多表联合搜索
 */
@Tag(name = "搜索管理", description = "全局搜索接口，支持文章、成员、课程、机构、学生等多类型搜索")
@RestController
@RequestMapping("/search")
@Validated
public class SearchController {
    
    @Autowired
    private SearchService searchService;
    
    /**
     * 全局搜索（POST方式）
     * 
     * @param request 搜索请求参数
     * @return 搜索结果
     */
    @Operation(
            summary = "全局搜索（POST）", 
            description = "根据关键词和类型搜索，支持多表联合搜索。" +
                    "支持的类型：article(文章)、member(成员)、course(课程)、organization(机构)、student(学生)。" +
                    "不指定types则搜索全部类型。"
    )
    @PostMapping("")
    public Result<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        SearchResponse response = searchService.search(request);
        return Result.success(response);
    }
    
    /**
     * 全局搜索（GET方式）
     * 
     * @param keyword 搜索关键词
     * @param types 搜索类型数组
     * @param page 页码
     * @param size 每页数量
     * @return 搜索结果
     */
    @Operation(
            summary = "全局搜索（GET）", 
            description = "根据关键词和类型搜索，支持多表联合搜索。" +
                    "支持的类型：article(文章)、member(成员)、course(课程)、organization(机构)、student(学生)。" +
                    "不指定types则搜索全部类型。"
    )
    @GetMapping("")
    public Result<SearchResponse> searchByGet(
            @Parameter(description = "搜索关键词", required = true, example = "医美")
            @RequestParam String keyword,
            
            @Parameter(description = "搜索类型数组", example = "article,member,course")
            @RequestParam(required = false) List<String> types,
            
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer page,
            
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") Integer size
    ) {
        SearchRequest request = new SearchRequest();
        request.setKeyword(keyword);
        request.setTypes(types);
        request.setPage(page);
        request.setSize(size);
        
        SearchResponse response = searchService.search(request);
        return Result.success(response);
    }
}

