package com.ms.product.service;

import com.ms.product.domain.entity.PmsProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsProductAttrValueService extends IService<PmsProductAttrValue> {

    void saveProductAttr(List<PmsProductAttrValue> objectList);

    List<PmsProductAttrValue> queryBySpuId(Long spuId);

    void updateAttr(Long spuId, List<PmsProductAttrValue> productAttrValues);
}
