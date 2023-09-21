package com.ms.order.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockVo implements Serializable {

    private static final long serialVersionUID = -2368605760369618350L;

    @JsonProperty("skuId")
    private Long skuId;

    @JsonProperty("hasStock")
    private Boolean hasStock;
}
