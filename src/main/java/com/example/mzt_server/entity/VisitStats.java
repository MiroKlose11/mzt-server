package com.example.mzt_server.entity;

import java.util.Date;

/**
 * 访问统计实体类
 */
public class VisitStats {
    private Long id;                 // 统计ID
    private Integer uvCount;         // UV数量
    private Integer pvCount;         // PV数量
    private Integer yesterdayUvCount; // 昨日UV数量
    private Integer yesterdayPvCount; // 昨日PV数量
    private Date statsDate;          // 统计日期
    private Date updateTime;         // 更新时间

    // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUvCount() {
        return uvCount;
    }

    public void setUvCount(Integer uvCount) {
        this.uvCount = uvCount;
    }

    public Integer getPvCount() {
        return pvCount;
    }

    public void setPvCount(Integer pvCount) {
        this.pvCount = pvCount;
    }

    public Integer getYesterdayUvCount() {
        return yesterdayUvCount;
    }

    public void setYesterdayUvCount(Integer yesterdayUvCount) {
        this.yesterdayUvCount = yesterdayUvCount;
    }

    public Integer getYesterdayPvCount() {
        return yesterdayPvCount;
    }

    public void setYesterdayPvCount(Integer yesterdayPvCount) {
        this.yesterdayPvCount = yesterdayPvCount;
    }

    public Date getStatsDate() {
        return statsDate;
    }

    public void setStatsDate(Date statsDate) {
        this.statsDate = statsDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
} 