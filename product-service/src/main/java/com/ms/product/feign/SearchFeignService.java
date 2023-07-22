package com.ms.product.feign;

import com.ms.common.api.Response;
import com.ms.common.to.es.SkuEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("moga-search-service")
public interface SearchFeignService {

    @PostMapping("/search/product/onshelf")
    Response upProductStatus(@RequestBody List<SkuEsModel> models);
}
