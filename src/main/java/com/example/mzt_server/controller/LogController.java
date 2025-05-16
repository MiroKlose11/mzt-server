package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.VisitStatsVO;
import com.example.mzt_server.common.vo.VisitTrendVO;
import com.example.mzt_server.common.vo.PageResult;
import com.example.mzt_server.common.vo.LogPageVO;
import com.example.mzt_server.service.ILogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "日志接口", description = "日志相关接口")
@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private ILogService logService;

    @Operation(summary = "获取访问统计", description = "获取系统访问量统计数据")
    @GetMapping("/visit-stats")
    public Result<VisitStatsVO> getVisitStats() {
        VisitStatsVO stats = logService.getVisitStats();
        return Result.success(stats);
    }

    @Operation(summary = "获取访问趋势", description = "获取系统访问趋势数据")
    @GetMapping("/visit-trend")
    public Result<VisitTrendVO> getVisitTrend(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        VisitTrendVO trend = logService.getVisitTrend(startDate, endDate);
        return Result.success(trend);
    }

    @Operation(summary = "日志分页列表", description = "获取系统日志分页列表")
    @GetMapping("/page")
    public Result<PageResult<LogPageVO>> getLogPage(
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String createTime,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        PageResult<LogPageVO> pageResult = logService.getLogPage(keywords, createTime, pageNum, pageSize);
        return Result.success(pageResult);
    }
} 