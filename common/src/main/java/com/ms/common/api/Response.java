package com.ms.common.api;

import com.ms.common.enums.BizStatusCode;

public class Response<T> {

    private final int code;

    private final String message;

    private final T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> Response<T> SUCCESS(T data) {
        return new Response<>(0, "success", data);
    }

    public static <T> Response<T> SUCCESS(BizStatusCode bizStatusCode) {
        return new Response<>(bizStatusCode.getCode(), bizStatusCode.getMessage(), null);
    }

    public static <T> Response<T> ERROR(int code, String message) {
        return new Response<>(code, message, null);
    }
}
