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
 * 优惠券领取历史记录
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_coupon_history")
@ApiModel(value = "SmsCouponHistory对象", description = "优惠券领取历史记录")
public class SmsCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("会员名字")
    private String memberNickName;

    @ApiModelProperty("获取方式[0->后台赠送；1->主动领取]")
    private Boolean getType;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("使用状态[0->未使用；1->已使用；2->已过期]")
    private Boolean useType;

    @ApiModelProperty("使用时间")
    private LocalDateTime useTime;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderSn;


}
