package com.example.mzt_server.common.exception;

import lombok.Getter;

/**
 * 错误枚举
 */
@Getter
public enum ErrorEnum {

    /**
     * 系统错误
     */
    SYSTEM_ERROR("B0001", "系统异常"),

    /**
     * 参数错误
     */
    PARAM_ERROR("A0400", "请求参数错误"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND("A0002", "用户不存在"),

    /**
     * 用户名或密码错误
     */
    USERNAME_PASSWORD_ERROR("A0003", "用户名或密码错误"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("A0004", "验证码错误或已过期"),

    /**
     * 账号已被禁用
     */
    ACCOUNT_DISABLED("A0005", "账号已被禁用"),

    /**
     * 无效的令牌
     */
    INVALID_TOKEN("A0006", "无效的令牌"),

    /**
     * 令牌已过期
     */
    TOKEN_EXPIRED("A0007", "令牌已过期"),

    /**
     * 没有权限
     */
    NO_PERMISSION("A0008", "没有权限"),

    /**
     * 未授权
     */
    UNAUTHORIZED("A0401", "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN("A0403", "禁止访问"),

    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND("A0404", "资源不存在"),
    
    /**
     * 文件大小超出限制
     */
    FILE_SIZE_EXCEEDED("A0410", "上传文件大小超出限制，最大允许5GB"),
    
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED("A0411", "文件上传失败，请检查文件格式或网络连接"),
    
    /**
     * 文件类型不支持
     */
    FILE_TYPE_NOT_SUPPORTED("A0412", "不支持的文件类型");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误消息
     */
    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
} 