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
 * 首页轮播广告
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_home_adv")
@ApiModel(value = "SmsHomeAdv对象", description = "首页轮播广告")
public class SmsHomeAdv implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("图片地址")
    private String pic;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("状态")
    private Boolean status;

    @ApiModelProperty("点击数")
    private Integer clickCount;

    @ApiModelProperty("广告详情连接地址")
    private String url;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("发布者")
    private Long publisherId;

    @ApiModelProperty("审核者")
    private Long authId;


}
