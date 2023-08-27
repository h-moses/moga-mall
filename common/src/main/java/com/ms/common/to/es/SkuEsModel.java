package com.ms.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuEsModel {

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImage;

    private Long saleCount;

    private Boolean hasStock;

    private Long hotScore;

    private Long brandId;

    private Long categoryId;

    private String brandName;

    private String brandImage;

    private String categoryName;

    private List<Attrs> attrs;

    @Data
    public static class Attrs {
        private Long attrId;

        private String attrName;

        private String attrValue;
    }
}
