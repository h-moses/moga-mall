package com.ms.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.product.domain.entity.PmsSkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsSkuInfoService extends IService<PmsSkuInfo> {

    List<PmsSkuInfo> getSkuBySpuId(Long spuId);

    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    Page<PmsSkuInfo> searchSku(String key, Long categoryId, Long brandId, Integer pageNum, Integer pageSize);
}
