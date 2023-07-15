package com.ms.warehouse.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.warehouse.entity.WmsWareInfo;
import com.ms.warehouse.entity.WmsWareSku;
import com.ms.warehouse.service.impl.WmsWareSkuServiceImpl;
import com.ms.warehouse.vo.StockVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     * 分页查询商品库存
     * @param skuId
     * @param wareId 仓库ID
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/ware/sku")
    public Response pageQuery(@RequestParam("skuId") Long skuId,
                              @RequestParam("wareId") Long wareId,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize) {
        Page<WmsWareSku> wmsWareSkuPage = wareSkuService.queryPage(skuId, wareId, pageNum, pageSize);
        return Response.SUCCESS(wmsWareSkuPage);
    }

    @PostMapping("/hasstock")
    public Response HasStockBySkuId(@RequestBody List<Long> skuIds) {
        List<StockVo> stock = wareSkuService.isStock(skuIds);
        return Response.SUCCESS(stock);
    }
}
