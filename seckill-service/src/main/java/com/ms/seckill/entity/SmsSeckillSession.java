package com.ms.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 秒杀活动场次
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_seckill_session")
@ApiModel(description = "秒杀活动场次")
public class SmsSeckillSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("场次名称")
    private String name;

    @ApiModelProperty("每日开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("每日结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("启用状态")
    private Boolean status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<SmsSeckillSkuRelation> skuRelationList;
}
