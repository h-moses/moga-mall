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
 * sku销售属性&值
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_sku_sale_attr_value")
@ApiModel(value = "PmsSkuSaleAttrValue对象", description = "sku销售属性&值")
public class PmsSkuSaleAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("sku_id")
    private Long skuId;

    @ApiModelProperty("attr_id")
    private Long attrId;

    @ApiModelProperty("销售属性名")
    private String attrName;

    @ApiModelProperty("销售属性值")
    private String attrValue;

    @ApiModelProperty("顺序")
    private Integer attrSort;


}
