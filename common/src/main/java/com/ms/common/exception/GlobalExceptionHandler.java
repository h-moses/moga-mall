package com.ms.common.exception;

import com.ms.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(-1)
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Response handleBizException(BizException e) {
        log.error("业务异常: {}", e.getMessage());
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(SysException.class)
    public Response handleSysException(SysException e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleSysException(MethodArgumentNotValidException e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return new Response<>(400, e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(Exception e) {
        log.error("全局异常: {}", e.getMessage(), e);
        return new Response<>(-1, "系统异常，请稍后再试", null);
    }
}
