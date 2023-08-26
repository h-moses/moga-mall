package com.ms.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.domain.vo.CategoryParamVo;
import com.ms.product.service.impl.PmsCategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Api(tags = "分类服务")
@RestController
@RequestMapping("/product/category")
public class PmsCategoryController {

    @Resource
    PmsCategoryServiceImpl categoryService;


    @PostMapping("/updateCategory")
    public Response updateCategoryCascade(@RequestBody CategoryParamVo categoryParamVo) {
        categoryService.updateCategory(categoryParamVo);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }


    @ApiOperation(value = "查询所有分类，以树形结构展示")
    @GetMapping("/treelist")
    public Response treeList() {
        try {
            return Response.SUCCESS(categoryService.getTreeList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "删除分类")
    @GetMapping("/delete")
    public Response deleteByIds(@RequestBody Integer[] ids) {
        return Response.SUCCESS(categoryService.deleteByIds(ids));
    }


    @ApiOperation(value = "新增分类")
    @PostMapping("/add")
    public Response add() {
        return Response.SUCCESS(null);
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("/update")
    public Response update() {
        return Response.SUCCESS(null);
    }
}
