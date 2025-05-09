package com.example.cms_mzt.common;

/**
 * 状态码枚举类
 */
public enum ResultCode {
    
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 失败
     */
    FAILED(500, "操作失败"),
    
    /**
     * 参数校验失败
     */
    VALIDATE_FAILED(400, "参数校验失败"),
    
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未登录或登录已过期"),
    
    /**
     * 未授权
     */
    FORBIDDEN(403, "没有相关权限");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 返回消息
     */
    private final String message;
    
    /**
     * 构造方法
     * 
     * @param code 状态码
     * @param message 返回消息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * 获取状态码
     * 
     * @return 状态码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 获取返回消息
     * 
     * @return 返回消息
     */
    public String getMessage() {
        return message;
    }
} 