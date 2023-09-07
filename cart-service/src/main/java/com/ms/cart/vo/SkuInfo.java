package com.ms.cart.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuInfo implements Serializable {
    private static final long serialVersionUID = -5861498330343521135L;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("spuId")
    private Long spuId;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("sku介绍描述")
    private String skuDesc;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    @ApiModelProperty("品牌id")
    private Long brandId;

    @ApiModelProperty("默认图片")
    private String skuDefaultImg;

    @ApiModelProperty("标题")
    private String skuTitle;

    @ApiModelProperty("副标题")
    private String skuSubtitle;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("销量")
    private Long saleCount;
}
