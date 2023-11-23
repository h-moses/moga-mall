package com.ms.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 秒杀商品通知订阅
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_seckill_sku_notice")
@ApiModel(value = "SmsSeckillSkuNotice对象", description = "秒杀商品通知订阅")
public class SmsSeckillSkuNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("member_id")
    private Long memberId;

    @ApiModelProperty("sku_id")
    private Long skuId;

    @ApiModelProperty("活动场次id")
    private Long sessionId;

    @ApiModelProperty("订阅时间")
    private LocalDateTime subcribeTime;

    @ApiModelProperty("发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("通知方式[0-短信，1-邮件]")
    private Boolean noticeType;


}
