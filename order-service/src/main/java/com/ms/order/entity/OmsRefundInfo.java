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

/**
 * <p>
 * 退款信息
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("oms_refund_info")
@ApiModel(value = "OmsRefundInfo对象", description = "退款信息")
public class OmsRefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("退款的订单")
    private Long orderReturnId;

    @ApiModelProperty("退款金额")
    private BigDecimal refund;

    @ApiModelProperty("退款交易流水号")
    private String refundSn;

    @ApiModelProperty("退款状态")
    private Boolean refundStatus;

    @ApiModelProperty("退款渠道[1-支付宝，2-微信，3-银联，4-汇款]")
    private Integer refundChannel;

    private String refundContent;


}
