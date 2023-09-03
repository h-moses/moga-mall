package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.entity.PmsSkuInfo;
import com.ms.product.mapper.PmsSkuInfoMapper;
import com.ms.product.service.IPmsSkuInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsSkuInfoServiceImpl extends ServiceImpl<PmsSkuInfoMapper, PmsSkuInfo> implements IPmsSkuInfoService {

    @Override
    public List<PmsSkuInfo> getSkuBySpuId(Long spuId) {
        LambdaQueryWrapper<PmsSkuInfo> queryWrapper = new LambdaQueryWrapper<PmsSkuInfo>().eq(PmsSkuInfo::getSpuId, spuId);
        return list(queryWrapper);
    }

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        save(pmsSkuInfo);
    }

    @Override
    public Page<PmsSkuInfo> searchSku(String key, Long categoryId, Long brandId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<PmsSkuInfo> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(key)) {
            queryWrapper.and(w -> {w.eq(PmsSkuInfo::getSkuId, key).or().like(PmsSkuInfo::getSkuName, key);});
        }
        if (StringUtils.hasText(String.valueOf(categoryId))) {
            queryWrapper.eq(PmsSkuInfo::getCategoryId, categoryId);
        }
        if (StringUtils.hasText(String.valueOf(brandId))) {
            queryWrapper.eq(PmsSkuInfo::getBrandId, brandId);
        }
        Page<PmsSkuInfo> infoPage = new Page<>(pageNum, pageSize);
        return page(infoPage, queryWrapper);
    }

    @Override
    public PmsSkuInfo getSkuInfoBySkuId(Long skuId) {
        LambdaQueryWrapper<PmsSkuInfo> wrapper = new LambdaQueryWrapper<PmsSkuInfo>().eq(PmsSkuInfo::getSkuId, skuId);
        return getOne(wrapper);
    }
}
