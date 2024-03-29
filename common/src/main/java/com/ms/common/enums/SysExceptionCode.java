package com.ms.common.enums;

public enum SysExceptionCode {

    SAVE_DB_ERROR(700, "插入数据库失败"),
    UPDATE_DB_ERROR(701, "更新数据库失败"),
    UPLOAD_OSS_ERROR(702, "上传文件失败"),
    URL_NOT_EXIST(703, "文件链接不存在"),
    PRODUCT_ON_SHELF_ERROR(704, "商品上架异常"),
    PRODUCT_ADD_TO_CART_ERROR(705, "商品添加异常"),;

    private final int code;
    private final String message;

    SysExceptionCode(int code) {
        this.code = code;
        this.message = "";
    }

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
