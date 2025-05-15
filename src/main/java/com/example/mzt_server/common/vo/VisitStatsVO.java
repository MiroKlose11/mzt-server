package com.example.mzt_server.common.vo;

/**
 * 访问量统计视图对象
 */
public class VisitStatsVO {
    private Integer todayUvCount;   // 今日独立访客数 (UV)
    private Integer totalUvCount;   // 累计独立访客数 (UV)
    private Double uvGrowthRate;    // 独立访客增长率
    private Integer todayPvCount;   // 今日页面浏览量 (PV)
    private Integer totalPvCount;   // 累计页面浏览量 (PV)
    private Double pvGrowthRate;    // 页面浏览量增长率

    public Integer getTodayUvCount() {
        return todayUvCount;
    }

    public void setTodayUvCount(Integer todayUvCount) {
        this.todayUvCount = todayUvCount;
    }

    public Integer getTotalUvCount() {
        return totalUvCount;
    }

    public void setTotalUvCount(Integer totalUvCount) {
        this.totalUvCount = totalUvCount;
    }

    public Double getUvGrowthRate() {
        return uvGrowthRate;
    }

    public void setUvGrowthRate(Double uvGrowthRate) {
        this.uvGrowthRate = uvGrowthRate;
    }

    public Integer getTodayPvCount() {
        return todayPvCount;
    }

    public void setTodayPvCount(Integer todayPvCount) {
        this.todayPvCount = todayPvCount;
    }

    public Integer getTotalPvCount() {
        return totalPvCount;
    }

    public void setTotalPvCount(Integer totalPvCount) {
        this.totalPvCount = totalPvCount;
    }

    public Double getPvGrowthRate() {
        return pvGrowthRate;
    }

    public void setPvGrowthRate(Double pvGrowthRate) {
        this.pvGrowthRate = pvGrowthRate;
    }
} 