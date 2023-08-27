package com.ms.user.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "TokenPair对象", description = "登录接口返回的token对")
public class TokenPair implements Serializable {

    private static final long serialVersionUID = -8614711611496998950L;

    @ApiModelProperty(value = "访问token")
    String accessToken;

    @ApiModelProperty(value = "刷新token")
    String refreshToken;

    @ApiModelProperty(value = "访问token过期时间")
    Date accessTokenExpiration;

    @ApiModelProperty(value = "刷新token过期时间")
    Date refreshTokenExpiration;
}
