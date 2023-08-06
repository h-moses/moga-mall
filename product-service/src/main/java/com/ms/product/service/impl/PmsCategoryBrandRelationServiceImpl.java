package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ms.product.domain.entity.PmsBrand;
import com.ms.product.domain.entity.PmsCategory;
import com.ms.product.domain.entity.PmsCategoryBrandRelation;
import com.ms.product.mapper.PmsBrandMapper;
import com.ms.product.mapper.PmsCategoryBrandRelationMapper;
import com.ms.product.mapper.PmsCategoryMapper;
import com.ms.product.service.IPmsCategoryBrandRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌分类关联 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsCategoryBrandRelationServiceImpl extends ServiceImpl<PmsCategoryBrandRelationMapper, PmsCategoryBrandRelation> implements IPmsCategoryBrandRelationService {

    @Resource
    PmsBrandMapper brandMapper;

    @Resource
    PmsCategoryMapper categoryMapper;

    @Override
    public List<PmsCategoryBrandRelation> categoryOfBrand(Long brandId) {
        LambdaQueryWrapper<PmsCategoryBrandRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsCategoryBrandRelation::getBrandId, brandId);
        return list(queryWrapper);
    }

    @Override
    public boolean saveCategoryOfBrand(Long brandId, Long categoryId) {
        // 查询品牌信息
        PmsBrand pmsBrand = brandMapper.selectById(brandId);
        // 查询分类信息
        PmsCategory pmsCategory = categoryMapper.selectById(categoryId);

        PmsCategoryBrandRelation relation = new PmsCategoryBrandRelation();
        relation.setBrandId(brandId);
        relation.setBrandName(pmsBrand.getName());
        relation.setCategoryId(categoryId);
        relation.setCategoryName(pmsCategory.getName());
        return save(relation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        PmsCategoryBrandRelation brandRelation = new PmsCategoryBrandRelation();
        brandRelation.setBrandId(brandId);
        brandRelation.setBrandName(name);
        LambdaUpdateWrapper<PmsCategoryBrandRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PmsCategoryBrandRelation::getBrandId, brandId);
        update(brandRelation, updateWrapper);
    }

    @Override
    public void updateCategory(Long categoryId, String name) {
        PmsCategoryBrandRelation categoryBrandRelation = new PmsCategoryBrandRelation();
        categoryBrandRelation.setCategoryId(categoryId);
        categoryBrandRelation.setCategoryName(name);
        LambdaUpdateWrapper<PmsCategoryBrandRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PmsCategoryBrandRelation::getCategoryId, categoryId);
        update(categoryBrandRelation, updateWrapper);
    }

    @Override
    public List<PmsCategoryBrandRelation> getBrandByCategoryId(Long categoryId) {
        LambdaQueryWrapper<PmsCategoryBrandRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsCategoryBrandRelation::getCategoryId, categoryId);
        return list(queryWrapper);
    }
}
