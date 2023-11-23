package com.ms.order.feign;

import com.ms.common.api.Response;
import com.ms.order.vo.UserAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("moga-user-service")
public interface UserServiceFeign {

    @GetMapping("/user/address/{userId}")
    Response<List<UserAddressVo>> queryAddress(@PathVariable("userId") Long userId);

    @GetMapping("/user/address/detail/{addressId}")
    Response<UserAddressVo> queryAddressById(@PathVariable("addressId") Long addressId);

}