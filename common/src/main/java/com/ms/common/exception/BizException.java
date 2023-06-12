package com.ms.common.exception;

import com.ms.common.enums.BizStatusCode;

public class BizException extends RuntimeException {

    private final int code;

    private final String message;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(BizStatusCode bizStatusCode) {
        super(bizStatusCode.getMessage());
        this.code = bizStatusCode.getCode();
        this.message = bizStatusCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
