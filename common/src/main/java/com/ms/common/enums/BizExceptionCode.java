package com.ms.common.enums;

public enum BizExceptionCode {

    PARAM_ERROR(400, "参数错误");

    private final int code;
    private final String message;

    BizExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
