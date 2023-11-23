package com.ms.seckill.entity;

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
 * 秒杀活动商品关联
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_seckill_sku_relation")
@ApiModel(description = "秒杀活动商品关联")
public class SmsSeckillSkuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("活动id")
    private Long promotionId;

    @ApiModelProperty("活动场次id")
    private Long promotionSessionId;

    @ApiModelProperty("商品id")
    private Long skuId;

    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀总量")
    private Integer seckillCount;

    @ApiModelProperty("每人限购数量")
    private Integer seckillLimit;

    @ApiModelProperty("排序")
    private Integer seckillSort;


}
