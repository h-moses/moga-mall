package com.ms.product.service;

import com.ms.product.domain.entity.PmsCategoryBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsCategoryBrandRelationService extends IService<PmsCategoryBrandRelation> {

    List<PmsCategoryBrandRelation> categoryOfBrand(Long brandId);

    boolean saveCategoryOfBrand(Long brandId, Long categoryId);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long categoryId, String name);

    List<PmsCategoryBrandRelation> getBrandByCategoryId(Long categoryId);
}
