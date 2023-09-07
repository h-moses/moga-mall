package com.ms.cart.feign;

import com.ms.cart.vo.SkuInfo;
import com.ms.common.api.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("moga-product-service")
public interface ProductFeign {

    @GetMapping("/product/sku/info")
    Response<SkuInfo> querySkuInfo(@RequestParam("skuId") Long skuId);

    @GetMapping("/product/sku/sale/info/{skuId}")
    Response<String> querySkuSaleAttr(@PathVariable("skuId") Long skuId);
}
