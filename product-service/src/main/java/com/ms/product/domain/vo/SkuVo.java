package com.ms.product.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuVo {

    private String skuName;

    private BigDecimal price;

    private String skuTitle;

    private String skuSubTitle;

    private List<Image> images;

    private List<String> descar;

    private Integer fullCount;

    private BigDecimal discount;

    private Integer countStatus;

    private List<String> desc;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private Integer priceStatus;

    private List<AttrVo> attr;
}
