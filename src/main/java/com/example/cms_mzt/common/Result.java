package com.example.cms_mzt.common;

import lombok.Data;

/**
 * 通用返回结果封装类
 * 
 * @param <T> 返回数据类型
 */
@Data
public class Result<T> {
    
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 返回消息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 私有构造方法
     */
    private Result() {}
    
    /**
     * 私有构造方法
     * 
     * @param code 状态码
     * @param message 返回消息
     * @param data 返回数据
     */
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功返回结果
     * 
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }
    
    /**
     * 成功返回结果
     * 
     * @param <T> 数据类型
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
    
    /**
     * 成功返回结果
     * 
     * @param <T> 数据类型
     * @param message 返回消息
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }
    
    /**
     * 失败返回结果
     * 
     * @param <T> 数据类型
     * @param resultCode 状态码枚举
     * @return Result对象
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }
    
    /**
     * 失败返回结果
     * 
     * @param <T> 数据类型
     * @param code 状态码
     * @param message 返回消息
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 失败返回结果
     * 
     * @param <T> 数据类型
     * @param message 返回消息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ResultCode.FAILED.getCode(), message, null);
    }
} 