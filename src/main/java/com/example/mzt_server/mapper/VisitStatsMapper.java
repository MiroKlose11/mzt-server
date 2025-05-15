package com.example.mzt_server.mapper;

import com.example.mzt_server.entity.VisitStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface VisitStatsMapper {
    
    /**
     * 获取今日的统计数据
     */
    @Select("SELECT * FROM visit_stats WHERE DATE(stats_date) = CURDATE()")
    VisitStats getTodayStats();
    
    /**
     * 获取特定日期的统计数据
     */
    @Select("SELECT * FROM visit_stats WHERE DATE(stats_date) = DATE(#{date})")
    VisitStats getStatsByDate(Date date);
    
    /**
     * 获取总UV数
     */
    @Select("SELECT SUM(uv_count) FROM visit_stats")
    Integer getTotalUvCount();
    
    /**
     * 获取总PV数
     */
    @Select("SELECT SUM(pv_count) FROM visit_stats")
    Integer getTotalPvCount();
    
    /**
     * 获取指定日期范围内的统计数据
     */
    @Select("SELECT * FROM visit_stats WHERE stats_date BETWEEN #{startDate} AND #{endDate} ORDER BY stats_date")
    List<VisitStats> getStatsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
} 