package com.ms.common.enums;

public enum PaymentMethod {

    ALIPAY("alipay", "支付宝"),
    WECHAT_PAY("wechat_pay", "微信支付");

    private final String code;
    private final String name;

    PaymentMethod(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
