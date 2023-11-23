package com.ms.warehouse.feign;

import com.ms.common.api.Response;
import com.ms.warehouse.domain.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "moga-order-service")
public interface OrderServiceFeign {

    @GetMapping("/order/info/{orderSn}")
    Response<OrderVo> queryOrderInfo(@PathVariable("orderSn") String orderSn);
}
