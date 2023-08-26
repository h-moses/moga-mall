package com.ms.common.exception;

import com.ms.common.enums.BizExceptionCode;
import com.ms.common.enums.BizStatusCode;

public class BizException extends RuntimeException {

    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(BizExceptionCode bizExceptionCode) {
        super(bizExceptionCode.getMessage());
        this.code = bizExceptionCode.getCode();
    }

    public BizException(BizStatusCode bizStatusCode) {
        super(bizStatusCode.getMessage());
        this.code = bizStatusCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
