package com.ms.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.product.domain.entity.PmsSkuInfo;
import com.ms.product.service.impl.PmsSkuInfoServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/pms-sku-info")
public class PmsSkuInfoController {

    @Resource
    PmsSkuInfoServiceImpl skuInfoService;

    @ApiOperation(value = "sku检索")
    @GetMapping("/search")
    public Response search(@RequestParam("key") String key,
                           @RequestParam("categoryId") Long categoryId,
                           @RequestParam("brandId") Long brandId,
                           @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize) {
        Page<PmsSkuInfo> infoPage = skuInfoService.searchSku(key, categoryId, brandId, pageNum, pageSize);
        return Response.SUCCESS(infoPage);
    }
}
