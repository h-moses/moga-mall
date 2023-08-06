package com.ms.warehouse.domain.entity;

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
 * 商品库存
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Getter
@Setter
@TableName("wms_ware_sku")
@ApiModel(value = "WmsWareSku对象", description = "商品库存")
public class WmsWareSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("sku_id")
    private Long skuId;

    @ApiModelProperty("仓库id")
    private Long wareId;

    @ApiModelProperty("库存数")
    private Integer stock;

    @ApiModelProperty("sku_name")
    private String skuName;

    @ApiModelProperty("锁定库存")
    private Integer stockLocked;


}
