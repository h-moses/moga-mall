package com.ms.user.controller;


import com.ms.common.api.Response;
import com.ms.user.service.impl.UserServiceImpl;
import com.ms.user.vo.UpdateUserInfoParamVo;
import com.ms.user.vo.UserRegisterParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-06-03
 */
@Api(tags = "用户服务")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @ApiOperation(value = "用户注册接口")
    @PostMapping("/register")
    public Response<Object> register(@RequestBody UserRegisterParamVo userRegisterParamVo) {
        return Response.SUCCESS(userService.register(userRegisterParamVo));
    }

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public Response<Object> login(@NotBlank @RequestParam("username") String username,
                          @NotBlank @RequestParam("password") String password) {
        return Response.SUCCESS(userService.login(username, password));
    }

    @ApiOperation(value = "个人信息修改接口")
    @PostMapping("/update")
    public Response<Object> updateInfo(@RequestBody UpdateUserInfoParamVo userInfoParamVo) {
        return null;
    }
}
