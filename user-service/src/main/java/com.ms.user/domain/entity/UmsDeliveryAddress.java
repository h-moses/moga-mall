package com.ms.user.domain.entity;

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
 * 会员收货地址
 * </p>
 *
 * @author ms
 * @since 2023-09-08
 */
@Getter
@Setter
@TableName("ums_delivery_address")
@ApiModel(value = "UmsDeliveryAddress对象", description = "会员收货地址")
public class UmsDeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("收货人姓名")
    private String name;

    @ApiModelProperty("收货人手机号码")
    private String phone;

    @ApiModelProperty("邮政编码")
    private String postCode;

    @ApiModelProperty("省份/直辖市")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区县")
    private String district;

    @ApiModelProperty("详细地址")
    private String detail;

    @ApiModelProperty("是否默认")
    private Boolean isDefault;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
