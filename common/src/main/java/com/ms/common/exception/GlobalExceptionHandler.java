package com.ms.common.exception;

import com.ms.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Response handleBizException(BizException e) {
        log.error("BizException: {}", e.getMessage());
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(SysException.class)
    public Response handleSysException(SysException e) {
        log.error("SysException: {}", e.getMessage(), e);
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return new Response<>(-1, "系统异常，请稍后再试", null);
    }
}
