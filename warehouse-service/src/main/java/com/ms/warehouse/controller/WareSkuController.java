package com.ms.warehouse.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.warehouse.domain.entity.WareSkuEntity;
import com.ms.warehouse.domain.vo.StockLockResVo;
import com.ms.warehouse.domain.vo.StockLockVo;
import com.ms.warehouse.domain.vo.StockVo;
import com.ms.warehouse.service.impl.WmsWareSkuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "商品库存")
@RestController
@RequestMapping("/warehouse/sku")
public class WareSkuController {

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
    @GetMapping("/stock-detail")
    public Response<Page<WareSkuEntity>> pageQuery(@RequestParam("skuId") Long skuId,
                                                   @RequestParam("wareId") Long wareId,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        Page<WareSkuEntity> wmsWareSkuPage = wareSkuService.queryPage(skuId, wareId, pageNum, pageSize);
        return Response.SUCCESS(wmsWareSkuPage);
    }

    @ApiOperation(value = "根据skuId获取库存")
    @PostMapping("/stock")
    public Response<List<StockVo>> HasStockBySkuId(@RequestBody List<Long> skuIds) {
        List<StockVo> stock = wareSkuService.isStock(skuIds);
        return Response.SUCCESS(stock);
    }

    @ApiOperation(value = "库存锁定")
    @PostMapping("/stock/lock")
    public Response<List<StockLockResVo>> lockStock(@RequestBody StockLockVo stockLockVo) {
        try {
            wareSkuService.lockStock(stockLockVo);
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } catch (BizException e) {
            return Response.ERROR(BizStatusCode.INSUFFICIENT_STOCK);
        }
    }
}
