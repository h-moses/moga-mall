package com.ms.order.feign;

import com.ms.common.api.Response;
import com.ms.order.vo.StockLockResVo;
import com.ms.order.vo.StockLockVo;
import com.ms.order.vo.StockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("moga-warehouse-service")
public interface WarehouseServiceFeign {


    @PostMapping("/warehouse/sku/stock")
    Response<List<StockVo>> HasStockBySkuId(@RequestBody List<Long> skuIds);


    @PostMapping("/stock/lock")
    Response<List<StockLockResVo>> lockStock(@RequestBody StockLockVo stockLockVo);
}
