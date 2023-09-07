package com.ms.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.entity.PmsSkuSaleAttrValue;
import com.ms.product.mapper.PmsSkuSaleAttrValueMapper;
import com.ms.product.service.IPmsSkuSaleAttrService;
import org.springframework.stereotype.Service;

@Service
public class PmsSkuSaleAttrServiceImpl  extends ServiceImpl<PmsSkuSaleAttrValueMapper, PmsSkuSaleAttrValue> implements IPmsSkuSaleAttrService {
    @Override
    public String querySaleAttr(Long skuId) {
        return getBaseMapper().querySaleAttr(skuId);
    }
}
