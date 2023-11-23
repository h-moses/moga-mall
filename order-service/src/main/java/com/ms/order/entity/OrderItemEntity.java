package com.ms.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单项信息
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("oms_order_item")
@ApiModel(value = "OmsOrderItem对象", description = "订单项信息")
public class OrderItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("order_id")
    private Long orderId;

    @ApiModelProperty("order_sn")
    private String orderSn;

    @ApiModelProperty("spu_id")
    private Long spuId;

    @ApiModelProperty("spu_name")
    private String spuName;

    @ApiModelProperty("spu_pic")
    private String spuPic;

    @ApiModelProperty("品牌")
    private String spuBrand;

    @ApiModelProperty("商品分类id")
    private Long categoryId;

    @ApiModelProperty("商品sku编号")
    private Long skuId;

    @ApiModelProperty("商品sku名字")
    private String skuName;

    @ApiModelProperty("商品sku图片")
    private String skuPic;

    @ApiModelProperty("商品sku价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("商品购买的数量")
    private Integer skuQuantity;

    @ApiModelProperty("商品销售属性组合（JSON）")
    private String skuAttrsVals;

    @ApiModelProperty("商品促销分解金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty("优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @ApiModelProperty("积分优惠分解金额")
    private BigDecimal integrationAmount;

    @ApiModelProperty("该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    @ApiModelProperty("赠送积分")
    private Integer giftIntegration;

    @ApiModelProperty("赠送成长值")
    private Integer giftGrowth;


}
