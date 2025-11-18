package com.example.mzt_server.service;

import com.example.mzt_server.dto.SearchRequest;
import com.example.mzt_server.dto.SearchResponse;

/**
 * 搜索服务接口
 */
public interface SearchService {
    
    /**
     * 全局搜索
     * 
     * @param request 搜索请求参数
     * @return 搜索结果
     */
    SearchResponse search(SearchRequest request);
}

