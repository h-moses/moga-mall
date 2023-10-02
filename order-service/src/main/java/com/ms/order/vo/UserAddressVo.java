package com.ms.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "订单收货地址")
@Data
public class UserAddressVo {


    @ApiModelProperty("地址ID")
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
    private Boolean isDefault;}
