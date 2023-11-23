package com.ms.order.feign;

import com.ms.common.api.Response;
import com.ms.order.vo.SpuInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("moga-product-service")
public interface ProductServiceFeign {

    @GetMapping("/product/spu/info/{skuId}")
    Response<SpuInfoVo> querySpuInfoBySkuId(@PathVariable("skuId") Long skuId);
}
