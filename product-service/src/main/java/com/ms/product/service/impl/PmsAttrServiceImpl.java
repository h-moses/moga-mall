package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.enums.ProductAttrType;
import com.ms.product.entity.PmsAttr;
import com.ms.product.entity.PmsAttrAttrgroupRelation;
import com.ms.product.entity.PmsAttrGroup;
import com.ms.product.entity.PmsCategory;
import com.ms.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.ms.product.mapper.PmsAttrGroupMapper;
import com.ms.product.mapper.PmsAttrMapper;
import com.ms.product.mapper.PmsCategoryMapper;
import com.ms.product.service.IPmsAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.vo.AttrGroupRelationParamVo;
import com.ms.product.vo.AttrParamVo;
import com.ms.product.vo.AttrResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsAttrServiceImpl extends ServiceImpl<PmsAttrMapper, PmsAttr> implements IPmsAttrService {

    @Resource
    PmsAttrAttrgroupRelationMapper attrGroupRelationMapper;

    @Resource
    PmsAttrGroupMapper attrGroupMapper;

    @Resource
    PmsCategoryMapper categoryMapper;

    @Transactional
    @Override
    public void saveAttr(AttrParamVo attrParamVo) {
        PmsAttr pmsAttr = new PmsAttr();
        BeanUtils.copyProperties(attrParamVo, pmsAttr);
        save(pmsAttr);
        // 保存关联关系
        if (ProductAttrType.BASE.getCode() == attrParamVo.getAttrType() && null != attrParamVo.getAttrGroupId()) {
            PmsAttrAttrgroupRelation relation = new PmsAttrAttrgroupRelation();
            relation.setAttrId(pmsAttr.getAttrId());
            relation.setAttrGroupId(attrParamVo.getAttrGroupId());
            attrGroupRelationMapper.insert(relation);
        }
    }

    @Override
    public Page<AttrResponseVo> attrPage(Long categoryId, Integer attrType, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<PmsAttr> queryWrapper = new LambdaQueryWrapper<>();
        if (0 != categoryId) {
            queryWrapper.eq(PmsAttr::getCategoryId, categoryId);
        }
        queryWrapper.eq(PmsAttr::getAttrType, attrType);
        Page<PmsAttr> pmsAttrPage = new Page<>(pageNum, pageSize);
        pmsAttrPage = page(pmsAttrPage, queryWrapper);
        List<AttrResponseVo> attrResponseVoList = pmsAttrPage.getRecords().stream().map(attr -> {
            AttrResponseVo attrResponseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attr, attrResponseVo);
            // 设置分组名
            PmsAttrAttrgroupRelation relation = attrGroupRelationMapper.selectOne(new LambdaQueryWrapper<PmsAttrAttrgroupRelation>().eq(PmsAttrAttrgroupRelation::getAttrId, attr.getAttrId()));
            if (null != relation) {
                PmsAttrGroup pmsAttrGroup = attrGroupMapper.selectById(relation.getAttrGroupId());
                attrResponseVo.setAttrGroupName(pmsAttrGroup.getAttrGroupName());
            }
            // 设置分类名
            PmsCategory pmsCategory = categoryMapper.selectById(attr.getCategoryId());
            attrResponseVo.setCategoryName(pmsCategory.getName());
            return attrResponseVo;
        }).collect(Collectors.toList());
        Page<AttrResponseVo> attrResponseVoPage = new Page<>();
        BeanUtils.copyProperties(pmsAttrPage, attrResponseVoPage);
        attrResponseVoPage.setRecords(attrResponseVoList);
        return attrResponseVoPage;
    }

    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        PmsAttr attr = getById(attrId);
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        BeanUtils.copyProperties(attr, attrResponseVo);
        if (ProductAttrType.BASE.getCode() == attr.getAttrType()) {
            // 设置分组信息
            PmsAttrAttrgroupRelation relation = attrGroupRelationMapper.selectOne(new LambdaQueryWrapper<PmsAttrAttrgroupRelation>().eq(PmsAttrAttrgroupRelation::getAttrId, attr));
            Assert.isTrue(relation != null, "不存在分组关系");
            PmsAttrGroup pmsAttrGroup = attrGroupMapper.selectById(relation.getAttrGroupId());
            attrResponseVo.setAttrName(pmsAttrGroup.getAttrGroupName());
        }
        // 设置分类信息
        PmsCategory pmsCategory = categoryMapper.selectById(attr.getCategoryId());
        Assert.isTrue(pmsCategory != null, "不存在分类");
        attrResponseVo.setCategoryName(pmsCategory.getName());
        return attrResponseVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrParamVo attrParamVo) {
        PmsAttr pmsAttr = new PmsAttr();
        BeanUtils.copyProperties(attrParamVo, pmsAttr);
        updateById(pmsAttr);
        // 修改分组关联
        PmsAttrAttrgroupRelation relation = new PmsAttrAttrgroupRelation();
        relation.setAttrId(attrParamVo.getAttrId());
        relation.setAttrGroupId(attrParamVo.getAttrGroupId());
        LambdaQueryWrapper<PmsAttrAttrgroupRelation> queryWrapper = new LambdaQueryWrapper<PmsAttrAttrgroupRelation>().eq(PmsAttrAttrgroupRelation::getAttrId, attrParamVo.getAttrId());
        Long selectCount = attrGroupRelationMapper.selectCount(queryWrapper);
        if (selectCount > 0) {
            attrGroupRelationMapper.update(relation, queryWrapper);
        } else {
            attrGroupRelationMapper.insert(relation);
        }
    }

    @Override
    public List<PmsAttr> getRelatedAttr(Long attrGroupId) {
        LambdaQueryWrapper<PmsAttrAttrgroupRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsAttrAttrgroupRelation::getAttrGroupId, attrGroupId);
        List<PmsAttrAttrgroupRelation> relationList = attrGroupRelationMapper.selectList(queryWrapper);
        List<Long> attrList = relationList.stream().map(PmsAttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        if (attrList.isEmpty()) {
            return null;
        }
        return listByIds(attrList);
    }

    @Override
    public void deleteRelation(AttrGroupRelationParamVo[] attrGroupRelationParamVos) {
        LambdaQueryWrapper<PmsAttrAttrgroupRelation> queryWrapper = new LambdaQueryWrapper<>();
        List<PmsAttrAttrgroupRelation> relationList = Arrays.stream(attrGroupRelationParamVos).map(attrGroupRelationParamVo -> {
            PmsAttrAttrgroupRelation relation = new PmsAttrAttrgroupRelation();
            relation.setAttrId(attrGroupRelationParamVo.getAttrId());
            relation.setAttrGroupId(attrGroupRelationParamVo.getAttrGroupId());
            return relation;
        }).collect(Collectors.toList());
        attrGroupRelationMapper.batchDeleteRelation(relationList);
    }

    @Override
    public Page<PmsAttr> getUnRelatedAttr(Long attrGroupId, Integer pageNum, Integer pageSize) {
        // 只能关联所属分类的属性
        PmsAttrGroup pmsAttrGroup = attrGroupMapper.selectById(attrGroupId);
        Long categoryId = pmsAttrGroup.getCategoryId();
        // 当前分类下的所有分组
        List<PmsAttrGroup> groupList = attrGroupMapper.selectList(new LambdaQueryWrapper<PmsAttrGroup>().eq(PmsAttrGroup::getCategoryId, categoryId));
        List<Long> groupIdList = groupList.stream().map(PmsAttrGroup::getAttrGroupId).collect(Collectors.toList());
        // 所有分组关联的属性
        List<PmsAttrAttrgroupRelation> relationList = attrGroupRelationMapper.selectList(new LambdaQueryWrapper<PmsAttrAttrgroupRelation>().in(!groupIdList.isEmpty(),PmsAttrAttrgroupRelation::getAttrGroupId, groupIdList));
        List<Long> attrIdList = relationList.stream().map(PmsAttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        // 找到同一分类下，未关联的属性
        LambdaQueryWrapper<PmsAttr> queryWrapper = new LambdaQueryWrapper<PmsAttr>().eq(PmsAttr::getCategoryId, categoryId).eq(PmsAttr::getAttrType, ProductAttrType.BASE).notIn(!attrIdList.isEmpty(),PmsAttr::getAttrId, attrIdList);

        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public List<Long> searchAttr(List<Long> attrIdList) {
        LambdaQueryWrapper<PmsAttr> wrapper = new LambdaQueryWrapper<PmsAttr>().in(PmsAttr::getAttrId, attrIdList).eq(PmsAttr::getSearchType, 1);
        List<PmsAttr> attrList = list(wrapper);
        return attrList.stream().map(PmsAttr::getAttrId).collect(Collectors.toList());
    }
}
