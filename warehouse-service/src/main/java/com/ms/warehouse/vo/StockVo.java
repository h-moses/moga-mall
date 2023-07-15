package com.ms.warehouse.vo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

@Data
public class StockVo {

    private Long skuId;

    private Boolean isStocked;
}
