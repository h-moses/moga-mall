package com.ms.product.api;

import com.ms.common.api.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("moga-warehouse-service")
public interface WareFeignService {

    @PostMapping("/wms-ware-sku/hasstock")
    public Response HasStockBySkuId(@RequestBody List<Long> skuIds);
}
