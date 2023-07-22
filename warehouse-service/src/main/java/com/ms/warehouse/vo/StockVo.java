package com.ms.warehouse.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockVo implements Serializable {

    private static final long serialVersionUID = 8167684388209882606L;

    @JsonProperty("skuId")
    private Long skuId;

    @JsonProperty("isStocked")
    private Boolean isStocked;
}
