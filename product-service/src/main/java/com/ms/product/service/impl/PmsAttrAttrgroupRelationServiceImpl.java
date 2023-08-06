package com.ms.product.service.impl;

import com.ms.product.domain.entity.PmsAttrAttrgroupRelation;
import com.ms.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.ms.product.service.IPmsAttrAttrgroupRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.vo.AttrGroupRelationParamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性&属性分组关联 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsAttrAttrgroupRelationServiceImpl extends ServiceImpl<PmsAttrAttrgroupRelationMapper, PmsAttrAttrgroupRelation> implements IPmsAttrAttrgroupRelationService {

    @Override
    public void addRelation(AttrGroupRelationParamVo[] relationParamVos) {
        List<PmsAttrAttrgroupRelation> relationList = Arrays.stream(relationParamVos).map(item -> {
            PmsAttrAttrgroupRelation relation = new PmsAttrAttrgroupRelation();
            BeanUtils.copyProperties(item, relation);
            return relation;
        }).collect(Collectors.toList());
        saveBatch(relationList);
    }
}
