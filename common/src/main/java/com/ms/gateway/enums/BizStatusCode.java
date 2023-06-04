package com.ms.gateway.enums;

/*
* 业务状态码
* */
public enum BizStatusCode {

    SUCCESS(0, "成功"),
    INVALID_PARAMETER(1001, "无效的参数"),
    USER_NOT_EXIST(1002, "用户不存在"),
    USER_ALREADY_EXIST(1003, "用户已存在"),
    PASSWORD_INCORRECT(1004, "密码错误"),
    INSUFFICIENT_BALANCE(2001, "余额不足"),
    INSUFFICIENT_STOCK(2002, "库存不足"),
    ORDER_NOT_EXIST(3001, "订单不存在"),
    ORDER_ALREADY_PAID(3002, "订单已支付"),
    ORDER_ALREADY_SHIPPED(3003, "订单已发货"),
    ORDER_ALREADY_RECEIVED(3004, "订单已收货"),
    CART_EMPTY(4001, "购物车为空"),
    CART_ITEM_NOT_EXIST(4002, "购物车商品不存在"),
    CART_ITEM_ALREADY_EXIST(4003, "购物车商品已存在"),
    ADDRESS_NOT_EXIST(5001, "收货地址不存在"),
    ADDRESS_ALREADY_EXIST(5002, "收货地址已存在"),
    GOODS_NOT_EXIST(6001, "商品不存在"),
    GOODS_ALREADY_EXIST(6002, "商品已存在"),
    GOODS_OFF_SHELF(6003, "商品已下架"),
    GOODS_ON_SHELF(6004, "商品已上架"),
    COUPON_NOT_EXIST(7001, "优惠券不存在"),
    COUPON_ALREADY_EXIST(7002, "优惠券已存在"),
    COUPON_EXPIRED(7003, "优惠券已过期"),
    COUPON_USED(7004, "优惠券已使用"),
    COUPON_INSUFFICIENT(7005, "优惠券数量不足"),
    COUPON_INVALID(7006, "优惠券无效"),
    REFUND_NOT_EXIST(8001, "退款申请不存在"),
    REFUND_ALREADY_EXIST(8002, "退款申请已存在"),
    REFUND_PROCESSING(8003, "退款申请处理中"),
    REFUND_SUCCESS(8004, "退款成功"),
    REFUND_FAIL(8005, "退款失败"),
    COMMENT_NOT_EXIST(9001, "评论不存在"),
    COMMENT_ALREADY_EXIST(9002, "评论已存在"),
    COMMENT_INVALID(9003, "评论无效"),
    FAVORITE_NOT_EXIST(10001, "收藏不存在"),
    FAVORITE_ALREADY_EXIST(10002, "收藏已存在"),
    FAVORITE_INVALID(10003, "收藏无效"),
    ADDRESS_INVALID(11001, "收货地址无效"),
    ORDER_INVALID(12001, "订单无效"),
    GOODS_INVALID(13001, "商品无效"),
    COUPON_LIMIT_EXCEEDED(14001, "优惠券领取次数超过限制");

    private final int code;
    private final String message;

    BizStatusCode(int code, String message) {
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
