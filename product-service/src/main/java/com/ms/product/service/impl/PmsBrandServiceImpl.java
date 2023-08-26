package com.ms.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.entity.PmsBrand;
import com.ms.product.domain.vo.BrandParamVo;
import com.ms.product.mapper.PmsBrandMapper;
import com.ms.product.service.IPmsBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {

    @Resource
    PmsCategoryBrandRelationServiceImpl categoryBrandRelationService;

    @Transactional
    @Override
    public void updateBrand(BrandParamVo brandParamVo) {
        PmsBrand brand = new PmsBrand();
        BeanUtils.copyProperties(brandParamVo, brand);
        updateById(brand);
        // 修改关联表中的品牌信息
        if (StringUtils.hasText(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
    }
}
