package com.ms.order.feign;

import com.ms.common.api.Response;
import com.ms.order.interceptor.CustomFeignInterceptor;
import com.ms.order.vo.StockLockResVo;
import com.ms.order.vo.StockLockVo;
import com.ms.order.vo.StockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "moga-warehouse-service", configuration = CustomFeignInterceptor.class)
public interface WarehouseServiceFeign {


    @PostMapping("/warehouse/sku/stock")
    Response<List<StockVo>> HasStockBySkuId(@RequestBody List<Long> skuIds);


    @PostMapping("/warehouse/sku/stock/lock")
    Response<List<StockLockResVo>> lockStock(@RequestBody StockLockVo stockLockVo);

    @ApiOperation(value = "扣减库存")
    @GetMapping("/warehouse/sku/stock/deduction")
    Response deduction(@RequestParam("orderSn") String orderSn);
}
