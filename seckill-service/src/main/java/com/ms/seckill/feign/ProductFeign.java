package com.ms.seckill.feign;

import com.ms.common.api.Response;
import com.ms.seckill.vo.SeckillSkuInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("moga-product-service")
public interface ProductFeign {

    @ApiOperation(value = "根据skuId获取信息")
    @GetMapping("/product/sku/info")
    Response<SeckillSkuInfoVo> info(@RequestParam("skuId") Long skuId);
}
