package com.example.mzt_server.common.vo;

import java.util.List;

/**
 * 访问趋势视图对象
 */
public class VisitTrendVO {
    private List<String> dates;   // 日期列表
    private List<Integer> pvList; // 浏览量(PV)列表
    private List<Integer> ipList; // IP数列表

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Integer> getPvList() {
        return pvList;
    }

    public void setPvList(List<Integer> pvList) {
        this.pvList = pvList;
    }

    public List<Integer> getIpList() {
        return ipList;
    }

    public void setIpList(List<Integer> ipList) {
        this.ipList = ipList;
    }
} 