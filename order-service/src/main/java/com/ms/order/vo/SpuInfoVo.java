package com.ms.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpuInfoVo {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品描述")
    private String spuDescription;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    @ApiModelProperty("品牌id")
    private Long brandId;

    private BigDecimal weight;

    @ApiModelProperty("上架状态[0 - 下架，1 - 上架]")
    private Integer status;

    private Date createTime;

    private Date updateTime;
}
