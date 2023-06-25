package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.product.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Api(tags = "商品服务")
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductServiceImpl productService;

    @GetMapping("/search")
    public Response search(@RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "categoryId", required = false) int categoryId,
                           @RequestParam(value = "orderBy", required = false) String orderBy,
                           @RequestParam(value = "pageNum", required = false) int pageNum,
                           @RequestParam(value = "pageSize", required = false) int pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return Response.SUCCESS(productService.searchProduct(keyword, categoryId, orderBy, pageNum, pageSize));
    }

    @GetMapping("/detail")
    public Response detail(@RequestParam("productId") int productId) {
        return Response.SUCCESS(productService.productInfo(productId));
    }

    @PostMapping("/seckill")
    public Response secKill(@RequestHeader(value = "username") String username,
                                   @RequestParam("productId") int productId) {
        return Response.SUCCESS(productService.secKillProduct(username, productId));
    }

    @PostMapping("/stock")
    public Response stock(@RequestParam("productId") int productId,
                          @RequestParam("stock") int stock) {

        return Response.SUCCESS(productService.updateStock(productId, stock));
    }
}
