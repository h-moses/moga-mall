package com.ms.user.controller;


import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.user.domain.entity.User;
import com.ms.user.domain.vo.UserAddressVo;
import com.ms.user.service.IUmsDeliveryAddressService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会员收货地址 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-09-08
 */
@Api(tags = "收货地址接口")
@RestController
@RequestMapping("/user/address")
public class UmsDeliveryAddressController {

    @Resource
    IUmsDeliveryAddressService deliveryAddressService;


    @GetMapping("/{userId}")
    public Response<List<UserAddressVo>> queryAddress(@PathVariable("userId") Long userId) {
        List<UserAddressVo> userAddressVo = deliveryAddressService.queryAddress(userId);
        if (null != userAddressVo) {
            return Response.SUCCESS(userAddressVo);
        } else {
            return Response.ERROR(BizStatusCode.ADDRESS_NOT_EXIST);
        }
    }

    @GetMapping("/detail/{addressId}")
    public Response<UserAddressVo> queryAddressById(@PathVariable("addressId") Long addressId) {
        UserAddressVo userAddressVo = deliveryAddressService.queryAddressById(addressId);
        if (null != userAddressVo) {
            return Response.SUCCESS(userAddressVo);
        } else {
            return Response.ERROR(BizStatusCode.ADDRESS_NOT_EXIST);
        }
    }
}
