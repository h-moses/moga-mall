package com.ms.product.service.impl;

import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.entity.Brand;
import com.ms.product.mapper.BrandMapper;
import com.ms.product.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Override
    public Brand brandInfo(String brandId) {
        Brand brand = getById(brandId);
        if (null == brand) {
            throw new BizException(BizStatusCode.BRAND_NOT_ExIST);
        }
        return brand;
    }

    @Override
    public boolean deleteByIds(int[] ids) {
        for (int id: ids) {
            baseMapper.deleteById(id);
        }
        return true;
    }
}
