package com.ms.gateway.enums;

public enum OrderStatus {
    CREATED(0, "已创建"),
    PAID(1, "已支付"),
    CONFIRMED(2, "已确认"),
    SHIPPED(3, "已发货"),
    DELIVERED(4, "已送达"),
    CANCELLED(5, "已取消"),
    REFUNDED(6, "已退款"),
    COMPLETED(7, "已完成");

    private final int code;
    private final String name;

    OrderStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus getByCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status code: " + code);
    }

    /**
     * 判断当前订单状态是否可以取消，如果订单状态为 CREATED、PAID 或 CONFIRMED，则可以取消，否则不可取消。
     * @return 是否可以取消
     */
    public boolean isCancelable() {
        return this == CREATED || this == PAID || this == CONFIRMED;
    }

    /**
     * 判断当前订单状态是否可以退款，如果订单状态为 PAID、CONFIRMED、SHIPPED 或 DELIVERED，则可以退款，否则不可退款。
     * @return 是否可以退款
     */
    public boolean isRefundable() {
        return this == PAID || this == CONFIRMED || this == SHIPPED || this == DELIVERED;
    }

    /**
     * 判断当前订单状态是否已完成，如果订单状态为 COMPLETED，则已完成，否则未完成。
     * @return 是否已完成
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * 判断当前订单状态是否已支付，如果订单状态为 PAID，则已支付，否则未支付。
     * @return 是否已支付
     */
    public boolean isPaid() {
        return this == PAID;
    }

    /**
     * 判断当前订单状态是否已确认，如果订单状态为 CONFIRMED，则已确认，否则未确认。
     * @return 是否已确认
     */
    public boolean isConfirmed() {
        return this == CONFIRMED;
    }

    /**
     * 判断当前订单状态是否已发货，如果订单状态为 SHIPPED，则已发货，否则未发货。
     * @return 是否已发货
     */
    public boolean isShipped() {
        return this == SHIPPED;
    }

    /**
     * 判断当前订单状态是否已送达，如果订单状态为 DELIVERED，则已送达，否则未送达。
     * @return 是否已送达
     */
    public boolean isDelivered() {
        return this == DELIVERED;
    }
}
