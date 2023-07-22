package com.ms.product.feign;

import com.ms.common.api.Response;
import com.ms.product.vo.StockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("moga-warehouse-service")
public interface WareFeignService {

    @PostMapping("/warehouse/sku/getStock")
    Response<List<StockVo>> HasStockBySkuId(@RequestBody List<Long> skuIds);
}
