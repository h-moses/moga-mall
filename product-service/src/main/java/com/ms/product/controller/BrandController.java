package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.product.entity.Brand;
import com.ms.product.service.impl.BrandServiceImpl;
import com.ms.product.vo.BrandParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
@Api(tags = "品牌服务")
@Validated
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Resource
    private BrandServiceImpl brandService;

    @ApiOperation(value = "查询所有品牌")
    @GetMapping("/search")
    public Response getAllBrand() {
        return Response.SUCCESS(brandService.list());
    }

    @ApiOperation(value = "查询特定品牌信息")
    @GetMapping("/info/{brand_id}")
    public Response getBrandInfo(@PathVariable("brand_id") String brandId) {
        return Response.SUCCESS(brandService.brandInfo(brandId));
    }

    @ApiOperation(value = "删除品牌")
    @GetMapping("/delete")
    public Response deleteByIds(@RequestBody int[] ids) {
        return Response.SUCCESS(brandService.deleteByIds(ids));
    }


    @ApiOperation(value = "新增品牌")
    @PostMapping("/add")
    public Response add(@RequestBody BrandParamVo brandParamVo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandParamVo, brand);
        return Response.SUCCESS(brandService.save(brand));
    }

    @ApiOperation(value = "修改品牌信息")
    @PostMapping("/update")
    public Response update(@RequestBody BrandParamVo brandParamVo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandParamVo, brand);
        return Response.SUCCESS(brandService.updateById(brand));
    }
}
