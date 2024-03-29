package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.entity.PmsAttrAttrgroupRelation;
import com.ms.product.domain.vo.AttrGroupRelationParamVo;

/**
 * <p>
 * 属性&属性分组关联 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsAttrAttrgroupRelationService extends IService<PmsAttrAttrgroupRelation> {

    void addRelation(AttrGroupRelationParamVo[] relationParamVos);
}
