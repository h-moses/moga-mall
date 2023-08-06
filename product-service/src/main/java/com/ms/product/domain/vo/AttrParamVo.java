package com.ms.product.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AttrParamVo对象", description = "商品属性")
public class AttrParamVo {

    @ApiModelProperty("属性id")
    private Long attrId;

    @ApiModelProperty("属性名")
    private String attrName;

    @ApiModelProperty("属性分组ID")
    private Long attrGroupId;

    @ApiModelProperty("是否需要检索[0-不需要，1-需要]")
    private Integer searchType;

    @ApiModelProperty("值类型[0-为单个值，1-可以选择多个值]")
    private Integer valueType;

    @ApiModelProperty("属性图标")
    private String icon;

    @ApiModelProperty("可选值列表[用逗号分隔]")
    private String valueSelect;

    @ApiModelProperty("属性类型[0-销售属性，1-基本属性")
    private Integer attrType;

    @ApiModelProperty("启用状态[0 - 禁用，1 - 启用]")
    private Long enable;

    @ApiModelProperty("所属分类")
    private Long categoryId;

    @ApiModelProperty("快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
    private Integer showDesc;
}
