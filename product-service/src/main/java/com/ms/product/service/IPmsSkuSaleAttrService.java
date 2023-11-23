package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.entity.PmsSkuSaleAttrValue;

import java.util.List;

public interface IPmsSkuSaleAttrService extends IService<PmsSkuSaleAttrValue> {
    List<String> querySaleAttr(Long skuId);
}
