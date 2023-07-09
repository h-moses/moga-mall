package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.service.impl.PmsBrandServiceImpl;
import com.ms.product.vo.BrandParamVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/pms-brand")
public class PmsBrandController {

    @Resource
    private PmsBrandServiceImpl brandService;

    @PostMapping("/updateBrand")
    public Response updateBrand(@RequestBody BrandParamVo brandParamVo) {
        brandService.updateBrand(brandParamVo);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }
}
