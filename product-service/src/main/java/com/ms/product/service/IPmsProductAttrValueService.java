package com.ms.product.service;

import com.ms.product.entity.PmsProductAttrValue;
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
}
