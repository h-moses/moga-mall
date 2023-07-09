package com.ms.product.service.impl;

import com.ms.product.entity.PmsProductAttrValue;
import com.ms.product.mapper.PmsProductAttrValueMapper;
import com.ms.product.service.IPmsProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsProductAttrValueServiceImpl extends ServiceImpl<PmsProductAttrValueMapper, PmsProductAttrValue> implements IPmsProductAttrValueService {

    @Override
    public void saveProductAttr(List<PmsProductAttrValue> objectList) {
        saveBatch(objectList);
    }
}
