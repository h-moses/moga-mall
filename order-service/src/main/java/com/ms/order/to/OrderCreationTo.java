package com.ms.order.to;

import com.ms.order.entity.OmsOrder;
import com.ms.order.entity.OmsOrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "创建订单")
@Data
public class OrderCreationTo {

    OmsOrder order;

    List<OmsOrderItem> orderItemList;

    BigDecimal amountPayable;
}
