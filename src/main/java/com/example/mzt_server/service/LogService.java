package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.LogPageVO;
import com.example.mzt_server.common.vo.PageResult;
import com.example.mzt_server.common.vo.VisitStatsVO;
import com.example.mzt_server.common.vo.VisitTrendVO;

/**
 * 日志服务接口
 */
public interface LogService {
    
    /**
     * 获取访问统计数据
     * 
     * @return 访问统计VO
     */
    VisitStatsVO getVisitStats();
    
    /**
     * 获取访问趋势
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 访问趋势VO
     */
    VisitTrendVO getVisitTrend(String startDate, String endDate);
    
    /**
     * 分页获取日志列表
     * 
     * @param keywords 关键字
     * @param createTime 时间范围（格式：startTime,endTime）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<LogPageVO> getLogPage(String keywords, String createTime, Integer pageNum, Integer pageSize);
} 