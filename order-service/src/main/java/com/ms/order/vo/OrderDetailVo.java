package com.ms.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel(description = "订单确认页信息")
@Data
public class OrderDetailVo {

    @ApiModelProperty(value = "防重令牌")
    String orderToken;

    @ApiModelProperty(value = "收货地址")
    List<UserAddressVo> addressVoList;

    @ApiModelProperty(value = "订单商品")
    List<OrderItemVo> orderItemVoList;

    Map<Long, Boolean> stockMap;

    @ApiModelProperty(value = "订单总额")
    BigDecimal totalPrice;
}
