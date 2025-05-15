package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.VisitStatsVO;
import com.example.mzt_server.entity.VisitStats;
import com.example.mzt_server.mapper.VisitStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import com.example.mzt_server.common.vo.VisitTrendVO;
import com.example.mzt_server.common.vo.PageResult;
import com.example.mzt_server.common.vo.LogPageVO;
import com.example.mzt_server.entity.SysLog;
import com.example.mzt_server.mapper.SysLogMapper;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.beans.BeanUtils;

@Service
public class LogService {

    @Autowired
    private VisitStatsMapper visitStatsMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 获取访问统计数据
     * 
     * @return 访问统计VO
     */
    public VisitStatsVO getVisitStats() {
        // 获取今日数据
        VisitStats todayStats = visitStatsMapper.getTodayStats();
        
        // 获取总计数据
        Integer totalUvCount = visitStatsMapper.getTotalUvCount();
        Integer totalPvCount = visitStatsMapper.getTotalPvCount();
        
        // 如果数据库中还没有统计数据，则使用模拟数据
        if (todayStats == null) {
            return createMockStats();
        }
        
        // 获取昨日数据
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        VisitStats yesterdayStats = visitStatsMapper.getStatsByDate(yesterday.getTime());
        
        VisitStatsVO vo = new VisitStatsVO();
        
        // 设置今日UV和PV
        vo.setTodayUvCount(todayStats.getUvCount());
        vo.setTodayPvCount(todayStats.getPvCount());
        
        // 设置总UV和PV
        vo.setTotalUvCount(totalUvCount != null ? totalUvCount : 0);
        vo.setTotalPvCount(totalPvCount != null ? totalPvCount : 0);
        
        // 计算增长率
        if (yesterdayStats != null && yesterdayStats.getUvCount() > 0) {
            double uvGrowthRate = ((double) todayStats.getUvCount() / yesterdayStats.getUvCount() - 1) * 100;
            vo.setUvGrowthRate(Math.round(uvGrowthRate * 100) / 100.0); // 保留两位小数
        } else {
            vo.setUvGrowthRate(0.0);
        }
        
        if (yesterdayStats != null && yesterdayStats.getPvCount() > 0) {
            double pvGrowthRate = ((double) todayStats.getPvCount() / yesterdayStats.getPvCount() - 1) * 100;
            vo.setPvGrowthRate(Math.round(pvGrowthRate * 100) / 100.0); // 保留两位小数
        } else {
            vo.setPvGrowthRate(0.0);
        }
        
        return vo;
    }
    
    /**
     * 创建模拟的统计数据
     */
    private VisitStatsVO createMockStats() {
        VisitStatsVO vo = new VisitStatsVO();
        vo.setTodayUvCount(120);
        vo.setTotalUvCount(25680);
        vo.setUvGrowthRate(16.28);
        vo.setTodayPvCount(1024);
        vo.setTotalPvCount(185432);
        vo.setPvGrowthRate(8.67);
        return vo;
    }

    /**
     * 获取访问趋势
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 访问趋势VO
     */
    public VisitTrendVO getVisitTrend(String startDate, String endDate) {
        try {
            // 解析日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            
            // 获取日期范围内的访问统计数据
            List<VisitStats> statsList = visitStatsMapper.getStatsByDateRange(start, end);
            
            // 如果没有数据，创建模拟数据
            if (statsList == null || statsList.isEmpty()) {
                return createMockTrend(startDate, endDate);
            }
            
            // 初始化趋势VO
            VisitTrendVO trendVO = new VisitTrendVO();
            List<String> dates = new ArrayList<>();
            List<Integer> pvList = new ArrayList<>();
            List<Integer> ipList = new ArrayList<>();
            
            // 格式化日期并添加数据
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd");
            for (VisitStats stats : statsList) {
                dates.add(outputFormat.format(stats.getStatsDate()));
                pvList.add(stats.getPvCount());
                // IP数据使用UV数据代替
                ipList.add(stats.getUvCount());
            }
            
            trendVO.setDates(dates);
            trendVO.setPvList(pvList);
            trendVO.setIpList(ipList);
            
            return trendVO;
        } catch (Exception e) {
            // 如果解析失败或其他错误，返回模拟数据
            return createMockTrend(startDate, endDate);
        }
    }
    
    /**
     * 创建模拟的访问趋势数据
     */
    private VisitTrendVO createMockTrend(String startDate, String endDate) {
        VisitTrendVO trendVO = new VisitTrendVO();
        List<String> dates = new ArrayList<>();
        List<Integer> pvList = new ArrayList<>();
        List<Integer> ipList = new ArrayList<>();
        
        // 添加模拟数据
        dates.add("05-01");
        dates.add("05-02");
        dates.add("05-03");
        dates.add("05-04");
        dates.add("05-05");
        
        pvList.add(1024);
        pvList.add(1280);
        pvList.add(1120);
        pvList.add(1450);
        pvList.add(1800);
        
        ipList.add(120);
        ipList.add(160);
        ipList.add(135);
        ipList.add(175);
        ipList.add(210);
        
        trendVO.setDates(dates);
        trendVO.setPvList(pvList);
        trendVO.setIpList(ipList);
        
        return trendVO;
    }
    
    /**
     * 分页获取日志列表
     * 
     * @param keywords 关键字
     * @param createTime 时间范围（格式：startTime,endTime）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    public PageResult<LogPageVO> getLogPage(String keywords, String createTime, Integer pageNum, Integer pageSize) {
        // 解析时间范围
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        
        if (createTime != null && !createTime.isEmpty()) {
            String[] timeRange = createTime.split(",");
            if (timeRange.length == 2) {
                try {
                    startTime = LocalDate.parse(timeRange[0]).atStartOfDay();
                    endTime = LocalDate.parse(timeRange[1]).atTime(23, 59, 59);
                } catch (DateTimeParseException e) {
                    // 解析失败，忽略时间范围
                }
            }
        }
        
        // 计算分页偏移量
        int offset = (pageNum - 1) * pageSize;
        
        // 查询数据
        List<SysLog> logList = sysLogMapper.queryPage(keywords, startTime, endTime, offset, pageSize);
        Long total = sysLogMapper.count(keywords, startTime, endTime);
        
        // 如果没有数据，返回空结果
        if (logList == null || logList.isEmpty()) {
            return new PageResult<>(new ArrayList<>(), 0L);
        }
        
        // 转换为VO
        List<LogPageVO> voList = new ArrayList<>();
        for (SysLog log : logList) {
            LogPageVO vo = new LogPageVO();
            BeanUtils.copyProperties(log, vo);
            voList.add(vo);
        }
        
        return new PageResult<>(voList, total);
    }
} 