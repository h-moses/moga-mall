package com.ms.common.constant;

public enum IndexConstant  {

    PRODUCT("product");

    private final String index;

    IndexConstant(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
