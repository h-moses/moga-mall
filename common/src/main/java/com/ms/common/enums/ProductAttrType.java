package com.ms.common.enums;

public enum ProductAttrType {
    BASE(0, "基本属性"),

    SALE(1, "销售属性");

    ProductAttrType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private final int code;

    private final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
