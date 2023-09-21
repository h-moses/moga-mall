package com.ms.warehouse.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockLockResVo implements Serializable {

    Long skuId;

    Integer quantity;

    Boolean locked;
}
