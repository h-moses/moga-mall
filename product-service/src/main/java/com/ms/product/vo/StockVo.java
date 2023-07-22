package com.ms.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockVo implements Serializable {

    private static final long serialVersionUID = -7600636577741878932L;

    @JsonProperty("skuId")
    private Long skuId;

    @JsonProperty("isStocked")
    private Boolean isStocked;
}
