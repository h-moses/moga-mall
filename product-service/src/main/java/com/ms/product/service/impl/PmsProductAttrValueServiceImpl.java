package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ms.product.entity.PmsProductAttrValue;
import com.ms.product.mapper.PmsProductAttrValueMapper;
import com.ms.product.service.IPmsProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<PmsProductAttrValue> queryBySpuId(Long spuId) {
        return list(new LambdaQueryWrapper<PmsProductAttrValue>().eq(PmsProductAttrValue::getSpuId, spuId));
    }

    @Override
    public void updateAttr(Long spuId, List<PmsProductAttrValue> productAttrValues) {
        remove(new LambdaQueryWrapper<PmsProductAttrValue>().eq(PmsProductAttrValue::getSpuId, spuId));
        List<PmsProductAttrValue> valueList = productAttrValues.stream().peek(item -> item.setSpuId(spuId)).collect(Collectors.toList());
        saveBatch(valueList);
    }
}
