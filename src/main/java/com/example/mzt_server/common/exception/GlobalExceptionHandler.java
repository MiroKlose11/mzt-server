package com.example.mzt_server.common.exception;

import com.example.mzt_server.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验异常: {}", message);
        return Result.error(ErrorEnum.PARAM_ERROR.getCode(), message);
    }

    /**
     * 参数绑定异常处理
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数绑定异常: {}", message);
        return Result.error(ErrorEnum.PARAM_ERROR.getCode(), message);
    }

    /**
     * 文件大小超出限制异常处理
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("文件大小超出限制: {}", e.getMessage());
        return Result.error(ErrorEnum.FILE_SIZE_EXCEEDED.getCode(), ErrorEnum.FILE_SIZE_EXCEEDED.getMessage());
    }

    /**
     * 文件上传异常处理
     */
    @ExceptionHandler(MultipartException.class)
    public Result<Void> handleMultipartException(MultipartException e) {
        log.warn("文件上传异常: {}", e.getMessage());
        return Result.error(ErrorEnum.FILE_UPLOAD_FAILED.getCode(), ErrorEnum.FILE_UPLOAD_FAILED.getMessage());
    }

    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error(ErrorEnum.SYSTEM_ERROR.getCode(), ErrorEnum.SYSTEM_ERROR.getMessage());
    }
} 