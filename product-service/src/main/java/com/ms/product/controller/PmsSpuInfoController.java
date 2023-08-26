package com.ms.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.domain.entity.PmsSpuInfo;
import com.ms.product.domain.vo.SaveSpuVo;
import com.ms.product.service.impl.PmsSpuInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * spu信息 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Api(tags = "spu服务")
@RestController
@RequestMapping("/product/spuInfo")
public class PmsSpuInfoController {

    @Resource
    PmsSpuInfoServiceImpl spuInfoService;

    @ApiOperation(value = "添加商品")
    @PostMapping("/savespu")
    public Response save(@RequestBody SaveSpuVo saveSpuVo) {
        spuInfoService.saveSpuInfo(saveSpuVo);

        return Response.SUCCESS(null);
    }

    @ApiOperation(value = "spu检索")
    @GetMapping("/search")
    public Response search(@RequestParam("key") String key,
                           @RequestParam("categoryId") Long categoryId,
                           @RequestParam("brandId") Long brandId,
                           @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize) {
        Page<PmsSpuInfo> infoPage = spuInfoService.searchSpu(key, categoryId, brandId, pageNum, pageSize);
        return Response.SUCCESS(infoPage);
    }

    @ApiOperation(value = "商品上架")
    @PostMapping("/{spuid}/onshelf")
    public Response onShelf(@PathVariable("spuid") Long spuId) {
        spuInfoService.upShelf(spuId);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

}
