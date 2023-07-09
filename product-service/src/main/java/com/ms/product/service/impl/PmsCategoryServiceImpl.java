package com.ms.product.service.impl;

import com.ms.product.entity.PmsCategory;
import com.ms.product.mapper.PmsCategoryMapper;
import com.ms.product.service.IPmsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.vo.CategoryParamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {

    @Resource
    PmsCategoryBrandRelationServiceImpl categoryBrandRelationService;

    @Transactional
    @Override
    public void updateCategory(CategoryParamVo categoryParamVo) {
        PmsCategory pmsCategory = new PmsCategory();
        BeanUtils.copyProperties(categoryParamVo, pmsCategory);
        updateById(pmsCategory);
        if (StringUtils.hasText(pmsCategory.getName())) {
            categoryBrandRelationService.updateCategory(pmsCategory.getCatId(), pmsCategory.getName());
        }
    }
}
