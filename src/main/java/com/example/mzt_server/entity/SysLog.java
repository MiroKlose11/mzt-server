package com.example.mzt_server.entity;

import com.example.mzt_server.common.enums.LogModuleEnum;
import java.time.LocalDateTime;

/**
 * 系统日志实体类
 */
public class SysLog {
    private Long id;                // 日志ID
    private LogModuleEnum module;   // 模块
    private String content;         // 日志内容
    private String requestUri;      // 请求URI
    private String method;          // 方法
    private String ip;              // IP地址
    private String region;          // 区域
    private String browser;         // 浏览器
    private String os;              // 操作系统
    private Long executionTime;     // 执行时间(毫秒)
    private Long createBy;          // 创建人ID
    private LocalDateTime createTime; // 创建时间
    private String operator;        // A操作人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogModuleEnum getModule() {
        return module;
    }

    public void setModule(LogModuleEnum module) {
        this.module = module;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
} 