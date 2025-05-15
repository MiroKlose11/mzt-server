package com.example.mzt_server.entity;

import java.util.Date;

/**
 * 访问日志实体类
 */
public class VisitLog {
    private Long id;            // 日志ID
    private String ip;          // 访问IP
    private String userAgent;   // 用户代理
    private String browser;     // 浏览器
    private String os;          // 操作系统
    private String device;      // 设备类型
    private String requestUrl;  // 请求URL
    private Date visitTime;     // 访问时间
    private String userId;      // 用户ID，未登录为空
    private String sessionId;   // 会话ID，用于计算UV
    private Boolean isFirstVisit; // 是否是首次访问
    
    // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getIsFirstVisit() {
        return isFirstVisit;
    }

    public void setIsFirstVisit(Boolean isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }
} 