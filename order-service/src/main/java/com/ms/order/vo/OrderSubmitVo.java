package com.ms.order.vo;

import com.ms.order.entity.OmsOrder;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "订单提交信息")
@Data
public class OrderSubmitVo implements Serializable {

    OmsOrder order;

    Long addressId;

    String payType;

    String orderToken;
}
