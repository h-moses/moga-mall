package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.product.entity.PmsAttr;
import com.ms.product.entity.PmsAttrGroup;
import com.ms.product.mapper.PmsAttrGroupMapper;
import com.ms.product.service.IPmsAttrGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.vo.AttrGroupDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsAttrGroupServiceImpl extends ServiceImpl<PmsAttrGroupMapper, PmsAttrGroup> implements IPmsAttrGroupService {

    @Resource
    PmsAttrServiceImpl attrService;

    @Override
    public Page<PmsAttrGroup> pageAttrGroup(Long categoryId, String keyword, Integer pageNum, Integer pageSize) {
        Page<PmsAttrGroup> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PmsAttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsAttrGroup::getCategoryId, categoryId);
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(pmsAttrGroupLambdaQueryWrapper -> {
                pmsAttrGroupLambdaQueryWrapper.eq(PmsAttrGroup::getAttrGroupId, keyword).or().like(PmsAttrGroup::getAttrGroupName, keyword);
            });
        }
        return page(page, queryWrapper);
    }

    @Override
    public List<AttrGroupDetailVo> getGroupDetail(Long categoryId) {
        LambdaQueryWrapper<PmsAttrGroup> queryWrapper = new LambdaQueryWrapper<PmsAttrGroup>().eq(PmsAttrGroup::getCategoryId, categoryId);
        List<PmsAttrGroup> groupList = list(queryWrapper);
        return groupList.stream().map(group -> {
            AttrGroupDetailVo detailVo = new AttrGroupDetailVo();
            BeanUtils.copyProperties(group, detailVo);

            List<PmsAttr> relatedAttr = attrService.getRelatedAttr(group.getAttrGroupId());
            detailVo.setAttrs(relatedAttr);
            return detailVo;
        }).collect(Collectors.toList());
    }
}
