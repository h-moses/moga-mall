package com.ms.order.feign;

import com.ms.common.api.Response;
import com.ms.order.interceptor.CustomFeignInterceptor;
import com.ms.order.vo.CartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "moga-cart-service", configuration = CustomFeignInterceptor.class)
public interface CartServiceFeign {

    @GetMapping("/cart/queryCheckedItem")
    Response<CartVo> queryCheckedItem();
}
