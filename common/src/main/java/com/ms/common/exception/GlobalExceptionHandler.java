package com.ms.common.exception;

import com.ms.common.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    public Response handleBizException(BizException e) {
        LOGGER.error("BizException: {}", e.getMessage());
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(SysException.class)
    public Response handleSysException(SysException e) {
        LOGGER.error("SysException: {}", e.getMessage(), e);
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        LOGGER.error("Exception: {}", e.getMessage(), e);
        return new Response<>(-1, "系统异常，请稍后再试", null);
    }
}
