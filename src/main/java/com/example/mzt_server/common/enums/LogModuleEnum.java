package com.example.mzt_server.common.enums;

/**
 * 日志模块枚举
 */
public enum LogModuleEnum {
    EXCEPTION("异常"),
    LOGIN("登录"),
    USER("用户"),
    DEPT("部门"),
    ROLE("角色"),
    MENU("菜单"),
    DICT("字典"),
    CONFIG("系统配置"),
    OTHER("其他");

    private final String description;

    LogModuleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 