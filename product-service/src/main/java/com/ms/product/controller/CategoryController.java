package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.product.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Api(tags = "分类服务")
@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryServiceImpl categoryService;

    @ApiOperation(value = "查询所有分类，以树形结构展示")
    @GetMapping("/treelist")
    public Response treeList() {
        return Response.SUCCESS(categoryService.getTreeList());
    }
}
