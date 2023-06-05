package com.ms.common.exception;

import com.ms.common.enums.SysExceptionCode;

public class SysException extends RuntimeException {

    private final int code;

    private final String message;

    public SysException(SysExceptionCode sysExceptionCode) {
        super(sysExceptionCode.getMessage());
        this.code = sysExceptionCode.getCode();
        this.message = sysExceptionCode.getMessage();
    }

    public SysException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
