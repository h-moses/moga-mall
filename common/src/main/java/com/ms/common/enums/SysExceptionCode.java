package com.ms.common.enums;

public enum SysExceptionCode {

    SAVE_DB_ERROR(700, "插入数据库失败"),
    UPDATE_DB_ERROR(701, "更新数据库失败");

    private final int code;
    private final String message;

    SysExceptionCode(int code, String message) {
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
