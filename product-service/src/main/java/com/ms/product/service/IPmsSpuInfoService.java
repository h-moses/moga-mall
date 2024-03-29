package com.ms.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.entity.PmsSpuInfo;
import com.ms.product.domain.vo.SaveSpuVo;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsSpuInfoService extends IService<PmsSpuInfo> {

    void saveSpuInfo(SaveSpuVo saveSpuVo);

    void upShelf(Long spuId);

    Page<PmsSpuInfo> searchSpu(String key, Long categoryId, Long brandId, Integer pageNum, Integer pageSize);

    PmsSpuInfo querySpuInfoBySkuId(Long skuId);
}
