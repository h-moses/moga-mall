package com.ms.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "BrandParamVo", description = "修改商品信息接收参数")
public class BrandParamVo implements Serializable {


    private static final long serialVersionUID = -4903531807026236738L;

    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("品牌名称")
    private String name;

    @ApiModelProperty("品牌Logo")
    private String logo;

    @ApiModelProperty("品牌描述")
    private String description;

    @ApiModelProperty("品牌状态（0：禁用，1：启用）")
    private Integer status;
}
