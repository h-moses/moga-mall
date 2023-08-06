package com.ms.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_category")
@ApiModel(value = "PmsCategory对象", description = "商品三级分类")
public class PmsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    @TableId(value = "cat_id", type = IdType.AUTO)
    private Long id;

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
