package com.ms.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.product.entity.PmsAttr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.vo.AttrGroupRelationParamVo;
import com.ms.product.vo.AttrParamVo;
import com.ms.product.vo.AttrResponseVo;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsAttrService extends IService<PmsAttr> {

    void saveAttr(AttrParamVo attrParamVo);

    Page<AttrResponseVo> attrPage(Long categoryId, Integer attrType, Integer pageNum, Integer pageSize);

    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttr(AttrParamVo attrParamVo);

    List<PmsAttr> getRelatedAttr(Long attrGroupId);

    void deleteRelation(AttrGroupRelationParamVo[] attrGroupRelationParamVos);

    Page<PmsAttr> getUnRelatedAttr(Long attrGroupId, Integer pageNum, Integer pageSize);

    List<Long> searchAttr(List<Long> attrIdList);
}


