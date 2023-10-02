package com.ms.common.to;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderTo implements Serializable {

    private static final long serialVersionUID = 6600839663274662768L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("订单号")
    private String orderSn;

    @JsonProperty("使用的优惠券")
    private Long couponId;

    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @JsonProperty("用户名")
    private String memberUsername;

    @JsonProperty("订单总额")
    private BigDecimal totalAmount;

    @JsonProperty("应付总额")
    private BigDecimal payAmount;

    @JsonProperty("运费金额")
    private BigDecimal freightAmount;

    @JsonProperty("促销优化金额（促销价、满减、阶梯价）")
    private BigDecimal promotionAmount;

    @JsonProperty("积分抵扣金额")
    private BigDecimal integrationAmount;

    @JsonProperty("优惠券抵扣金额")
    private BigDecimal couponAmount;

    @JsonProperty("后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;

    @JsonProperty("支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】")
    private Integer payType;

    @JsonProperty("订单来源[0->PC订单；1->app订单]")
    private Integer sourceType;

    @JsonProperty("订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】")
    private Integer status;

    @JsonProperty("物流公司(配送方式)")
    private String deliveryCompany;

    @JsonProperty("物流单号")
    private String deliverySn;

    @JsonProperty("自动确认时间（天）")
    private Integer autoConfirmDay;

    @JsonProperty("可以获得的积分")
    private Integer integration;

    @JsonProperty("可以获得的成长值")
    private Integer growth;

    @JsonProperty("发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer billType;

    @JsonProperty("发票抬头")
    private String billHeader;

    @JsonProperty("发票内容")
    private String billContent;

    @JsonProperty("收票人电话")
    private String billReceiverPhone;

    @JsonProperty("收票人邮箱")
    private String billReceiverEmail;

    @JsonProperty("收货人姓名")
    private String receiverName;

    @JsonProperty("收货人电话")
    private String receiverPhone;

    @JsonProperty("收货人邮编")
    private String receiverPostCode;

    @JsonProperty("省份/直辖市")
    private String receiverProvince;

    @JsonProperty("城市")
    private String receiverCity;

    @JsonProperty("区")
    private String receiverRegion;

    @JsonProperty("详细地址")
    private String receiverDetailAddress;

    @JsonProperty("订单备注")
    private String note;

    @JsonProperty("确认收货状态[0->未确认；1->已确认]")
    private Integer confirmStatus;

    @JsonProperty("删除状态【0->未删除；1->已删除】")
    private Integer deleteStatus;

    @JsonProperty("下单时使用的积分")
    private Integer useIntegration;

    @JsonProperty("支付时间")
    private LocalDateTime paymentTime;

    @JsonProperty("发货时间")
    private LocalDateTime deliveryTime;

    @JsonProperty("确认收货时间")
    private LocalDateTime receiveTime;

    @JsonProperty("评价时间")
    private LocalDateTime commentTime;

    @JsonProperty("修改时间")
    private LocalDateTime modifyTime;
}
