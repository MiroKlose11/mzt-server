package com.example.mzt_server.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用响应结果
 */
@Data
@Schema(description = "通用响应结果")
public class Result<T> {

    /**
     * 响应码
     */
    @Schema(description = "响应码", example = "00000")
    private String code;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

 /**
     * 响应消息
     */
    @Schema(description = "响应消息", example = "一切OK!")
    private String msg;

    /**
     * 私有构造函数
     */
    private Result() {
    }

    /**
     * 成功结果
     *
     * @param data 数据
     * @param <T> 数据类型
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("00000");
        result.setData(data);
        result.setMsg("一切OK!");
        return result;
    }

    /**
     * 成功结果（无数据）
     *
     * @param <T> 数据类型
     * @return 结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 错误结果
     *
     * @param code 错误码
     * @param msg 错误消息
     * @param <T> 数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误结果（默认错误码）
     *
     * @param msg 错误消息
     * @param <T> 数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String msg) {
        return error("B0001", msg);
    }
} 