package com.ms.product.service;

import com.ms.product.entity.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.vo.BrandParamVo;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsBrandService extends IService<PmsBrand> {

    void updateBrand(BrandParamVo brandParamVo);
}
