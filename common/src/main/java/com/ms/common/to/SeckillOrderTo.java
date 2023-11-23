package com.ms.common.to;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SeckillOrderTo implements Serializable {

    private static final long serialVersionUID = 4896762360314530413L;

    // 订单号
    String orderSn;

    // 场次Id
    Long promotionSessionId;

    // 商品Id
    Long skuId;

    // 秒杀价格
    BigDecimal seckillPrice;

    // 购买数量
    Integer count;

    // 用户Id
    String userId;
}
