package com.ms.order.to;

import com.ms.order.entity.OrderEntity;
import com.ms.order.entity.OrderItemEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "创建订单")
@Data
public class OrderCreationTo {

    OrderEntity order;

    List<OrderItemEntity> orderItemList;

    BigDecimal amountPayable;
}
