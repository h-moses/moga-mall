package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.entity.PmsSkuSaleAttrValue;

public interface IPmsSkuSaleAttrService extends IService<PmsSkuSaleAttrValue> {
    String querySaleAttr(Long skuId);
}
