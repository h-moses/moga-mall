package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.domain.entity.PmsCategoryBrandRelation;
import com.ms.product.domain.vo.BrandResponseVo;
import com.ms.product.service.impl.PmsCategoryBrandRelationServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/pms-category-brand-relation")
public class PmsCategoryBrandRelationController {

    @Resource
    private PmsCategoryBrandRelationServiceImpl categoryBrandRelationService;


    @ApiOperation(value = "获取品牌所属的分类")
    @GetMapping("/getCategoryByBrand")
    public Response categoryByBrand(@RequestParam("brandId") Long brandId) {
        if (!StringUtils.hasText(String.valueOf(brandId))) {
            throw new BizException(BizStatusCode.INVALID_PARAMETER);
        }
        List<PmsCategoryBrandRelation> relationList = categoryBrandRelationService.categoryOfBrand(brandId);
        return Response.SUCCESS(relationList);
    }

    @ApiOperation(value = "添加品牌和分类的关联")
    @PostMapping("/addCategoryOfBrand")
    public Response addCategoryOfBrand(@RequestParam("brandId") Long brandId,
                                       @RequestParam("categoryId") Long categoryId) {
        boolean saved = categoryBrandRelationService.saveCategoryOfBrand(brandId, categoryId);
        if (saved) {
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } else {
            return Response.ERROR(500, "品牌与分类关联失败");
        }
    }

    @ApiOperation(value = "查询分类关联的品牌ID和品牌名")
    @GetMapping("/brand/list")
    public Response getBrandOfCategory(@RequestParam("categoryId") Long categoryId) {
        List<PmsCategoryBrandRelation> brandRelationList = categoryBrandRelationService.getBrandByCategoryId(categoryId);
        List<List<PmsCategoryBrandRelation>> brandList = brandRelationList.stream().map(item -> {
            BrandResponseVo brandResponseVo = new BrandResponseVo();
            brandResponseVo.setBrandId(item.getBrandId());
            brandResponseVo.setBrandName(item.getBrandName());
            return brandRelationList;
        }).collect(Collectors.toList());
        return Response.SUCCESS(brandList);
    }
}
