package com.ms.product.controller;

import com.ms.common.api.Response;
import com.ms.product.service.IPmsSkuSaleAttrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/product/sku/sale")
public class PmsSkuSaleAttrController {

    @Resource
    IPmsSkuSaleAttrService skuSaleAttrService;

    @Resource

    @GetMapping("/info/{skuId}")
    public Response querySaleAttr(@PathVariable("skuId") Long skuId) {
        String s = skuSaleAttrService.querySaleAttr(skuId);
        return Response.SUCCESS(s);
    }
}
