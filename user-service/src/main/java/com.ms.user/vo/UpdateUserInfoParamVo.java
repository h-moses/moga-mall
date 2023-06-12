package com.ms.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "UpdateUserInfoParamVo", description = "个人信息修改接收参数")
public class UpdateUserInfoParamVo {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别（0：未知，1：男，2：女）")
    private Integer gender;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

}
