package com.ms.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("oms_payment_info")
@ApiModel(description = "支付信息表")
public class PaymentInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单号（对外业务号）")
    private String orderSn;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("支付宝交易流水号")
    private String alipayTradeNo;

    @ApiModelProperty("支付总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("交易内容")
    private String subject;

    @ApiModelProperty("支付状态")
    private String paymentStatus;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("确认时间")
    private LocalDateTime confirmTime;

    @ApiModelProperty("回调内容")
    private String callbackContent;

    @ApiModelProperty("回调时间")
    private LocalDateTime callbackTime;


}
