package com.ms.search.controller;

import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.to.es.SkuEsModel;
import com.ms.search.service.ProductSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class ProductSearchController {

    @Resource
    ProductSearchService productSearchService;

    @PostMapping("/product/onshelf")
    public Response upProductStatus(@RequestBody List<SkuEsModel> models) {

        try {
            boolean onShelf = productSearchService.onShelf(models);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }
}
