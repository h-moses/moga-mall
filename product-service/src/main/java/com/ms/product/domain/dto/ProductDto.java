package com.ms.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品分类ID")
    private Integer categoryId;

    @ApiModelProperty("商品品牌ID")
    private Integer brandId;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品库存")
    private Integer stock;

    @ApiModelProperty("商品销量")
    private Integer sales;

    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("商品状态（0：下架，1：上架）")
    private Integer status;
}
