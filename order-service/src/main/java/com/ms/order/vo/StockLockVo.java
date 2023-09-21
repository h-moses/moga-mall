package com.ms.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockLockVo implements Serializable {

    private static final long serialVersionUID = -4797724458722712804L;

    String orderSn;

    List<OrderItemVo> orderItemVoList;
}
