package com.ms.product.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaveSpuVo {

    private String spuName;

    private String spuDescription;

    private Long categoryId;

    private Long brandId;

    private BigDecimal weight;

    private Integer status;

    private List<String> decript;

    private List<String> images;

    private List<BaseAttrVo> baseAttrs;

    private List<SkuVo> skus;
}
