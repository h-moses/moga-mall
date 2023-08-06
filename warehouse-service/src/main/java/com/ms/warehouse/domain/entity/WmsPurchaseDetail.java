package com.ms.warehouse.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Getter
@Setter
@TableName("wms_purchase_detail")
@ApiModel(value = "WmsPurchaseDetail对象", description = "")
public class WmsPurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("采购单id")
    private Long purchaseId;

    @ApiModelProperty("采购商品id")
    private Long skuId;

    @ApiModelProperty("采购数量")
    private Integer skuNum;

    @ApiModelProperty("采购金额")
    private BigDecimal skuPrice;

    @ApiModelProperty("仓库id")
    private Long wareId;

    @ApiModelProperty("状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    private Integer status;


}
