package com.ms.user.controller;


import com.ms.common.api.Response;
import com.ms.common.constant.AuthConstant;
import com.ms.user.domain.dto.UserInfoDto;
import com.ms.user.domain.entity.TokenPair;
import com.ms.user.domain.vo.UpdateUserInfoParamVo;
import com.ms.user.domain.vo.UserRegisterParamVo;
import com.ms.user.service.impl.UserServiceImpl;
import com.ms.user.utils.TokenUtils;
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
    public Response<TokenPair> login(@NotBlank @RequestParam("username") String username,
                                     @NotBlank @RequestParam("password") String password) {
        UserInfoDto res = userService.login(username, password);
        return Response.SUCCESS(TokenUtils.generateTokenPair(res.getId(), res.getUsername()));
    }

    @ApiOperation(value = "个人信息修改接口")
    @PostMapping("/update")
    public Response<Object> updateInfo(@RequestHeader(AuthConstant.USER_HEADER) String username,
                                       @RequestBody UpdateUserInfoParamVo userInfoParamVo) {
        return Response.SUCCESS(userService.updateInfo(username, userInfoParamVo));
    }

    @ApiOperation(value = "个人信息查询接口")
    @GetMapping("/info")
    public Response<Object> queryInfo(@RequestHeader(AuthConstant.USER_HEADER) String claim) {
        int i = 10 / 0;
        return Response.SUCCESS(userService.queryInfo(claim.split("_")[1]));
    }
}
