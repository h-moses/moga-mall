package com.ms.warehouse.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.warehouse.entity.WmsWareInfo;
import com.ms.warehouse.entity.WmsWareSku;
import com.ms.warehouse.service.impl.WmsWareSkuServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品库存 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@RestController
@RequestMapping("/wms-ware-sku")
public class WmsWareSkuController {

    @Resource
    WmsWareSkuServiceImpl wareSkuService;


    @GetMapping("/ware/sku")
    public Response pageQuery(@RequestParam("skuId") Long skuId,
                              @RequestParam("wareId") Long wareId,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize) {
        Page<WmsWareSku> wmsWareSkuPage = wareSkuService.queryPage(skuId, wareId, pageNum, pageSize);
        return Response.SUCCESS(wmsWareSkuPage);
    }
}
