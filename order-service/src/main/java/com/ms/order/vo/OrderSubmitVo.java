package com.ms.order.vo;

import com.ms.order.entity.OrderEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "订单提交信息")
@Data
public class OrderSubmitVo implements Serializable {

    @ApiModelProperty(value = "订单信息")
    OrderEntity order;

    @ApiModelProperty(value = "订单收货地址")
    Long addressId;

    @ApiModelProperty(value = "支付方式")
    String payType;

    @ApiModelProperty(value = "订单防重令牌")
    String orderToken;
}
