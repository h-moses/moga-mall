package com.ms.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_attr")
@ApiModel(value = "PmsAttr对象", description = "商品属性")
public class PmsAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("属性id")
    @TableId(value = "attr_id", type = IdType.AUTO)
    private Long attrId;

    @ApiModelProperty("属性名")
    private String attrName;

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
    private Long status;

    @ApiModelProperty("所属分类")
    private Long categoryId;

    @ApiModelProperty("快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
    private Integer showDesc;


}
