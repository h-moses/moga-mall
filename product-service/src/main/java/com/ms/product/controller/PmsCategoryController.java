package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.service.impl.PmsCategoryServiceImpl;
import com.ms.product.vo.CategoryParamVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/pms-category")
public class PmsCategoryController {

    @Resource
    PmsCategoryServiceImpl categoryService;


    @PostMapping("/updateCategory")
    public Response updateCategoryCascade(@RequestBody CategoryParamVo categoryParamVo) {
        categoryService.updateCategory(categoryParamVo);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }
}
