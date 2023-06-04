package com.ms.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@ApiModel(value = "UserRegisterParamVo", description = "用户注册接收参数")
public class UserRegisterParamVo {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不允许为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不允许为空")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别（0：未知，1：男，2：女）")
    private Integer gender;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;
}
