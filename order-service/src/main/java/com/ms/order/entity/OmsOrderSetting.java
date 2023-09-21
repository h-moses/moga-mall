package com.ms.order.entity;

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
 * 订单配置信息
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("oms_order_setting")
@ApiModel(value = "OmsOrderSetting对象", description = "订单配置信息")
public class OmsOrderSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("秒杀订单超时关闭时间(分)")
    private Integer flashOrderOvertime;

    @ApiModelProperty("正常订单超时时间(分)")
    private Integer normalOrderOvertime;

    @ApiModelProperty("发货后自动确认收货时间（天）")
    private Integer confirmOvertime;

    @ApiModelProperty("自动完成交易时间，不能申请退货（天）")
    private Integer finishOvertime;

    @ApiModelProperty("订单完成后自动好评时间（天）")
    private Integer commentOvertime;

    @ApiModelProperty("会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】")
    private Integer memberLevel;


}
