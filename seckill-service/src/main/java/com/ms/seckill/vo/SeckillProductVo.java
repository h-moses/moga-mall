package com.ms.seckill.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeckillProductVo implements Serializable {
    private static final long serialVersionUID = 2981957229195995575L;

    @ApiModelProperty("id")
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

    @ApiModelProperty("商品详细信息")
    private SeckillSkuInfoVo skuInfoVo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String randomCode;
}
