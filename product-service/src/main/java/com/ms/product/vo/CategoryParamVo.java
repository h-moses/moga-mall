package com.ms.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "CategoryParamVo对象", description = "商品三级分类")
public class CategoryParamVo implements Serializable {

    private static final long serialVersionUID = -4370076251531879366L;

    @ApiModelProperty("分类id")
    private Long catId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("层级")
    private Integer level;

    @ApiModelProperty("是否显示[0-不显示，1显示]")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标地址")
    private String icon;

    @ApiModelProperty("计量单位")
    private String productUnit;

    @ApiModelProperty("商品数量")
    private Integer productCount;
}
