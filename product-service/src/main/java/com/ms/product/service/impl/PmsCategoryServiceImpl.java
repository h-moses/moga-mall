package com.ms.product.service.impl;

import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.dto.CategoryTreeDto;
import com.ms.product.entity.PmsCategory;
import com.ms.product.mapper.PmsCategoryMapper;
import com.ms.product.service.IPmsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.vo.CategoryParamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {

    @Resource
    PmsCategoryBrandRelationServiceImpl categoryBrandRelationService;

    @Transactional
    @Override
    public void updateCategory(CategoryParamVo categoryParamVo) {
        PmsCategory pmsCategory = new PmsCategory();
        BeanUtils.copyProperties(categoryParamVo, pmsCategory);
        updateById(pmsCategory);
        if (StringUtils.hasText(pmsCategory.getName())) {
            categoryBrandRelationService.updateCategory(pmsCategory.getCatId(), pmsCategory.getName());
        }
    }

    @Override
    public List<CategoryTreeDto> getTreeList() {
        // 查询所有分类
        List<PmsCategory> categoryList = list();

        // 找出一级分类
        List<PmsCategory> primary = categoryList.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());

        List<CategoryTreeDto> primaryDto = com.ms.common.utils.BeanUtils.copyList(primary, CategoryTreeDto.class);
        primaryDto.forEach(categoryTreeDto -> categoryTreeDto.setChildren(getCategoryChildren(categoryTreeDto, categoryList)));

        return primaryDto;
    }

    @Override
    public boolean deleteByIds(Integer[] ids) {
        List<PmsCategory> categoryList = list();
        for (Integer id: ids) {
            boolean res = categoryList.stream().anyMatch(c -> c.getParentId() == id.intValue());
            if (res) {
                throw new BizException(BizStatusCode.CATEGORY_HAS_CHILDREN);
            }
        }
        int deleted = baseMapper.deleteBatchIds(Arrays.asList(ids));
        return deleted > 0;
    }

    private List<CategoryTreeDto> getCategoryChildren(CategoryTreeDto primaryDto, List<PmsCategory> source) {
        List<PmsCategory> children = source.stream().filter(category -> category.getParentId().equals(primaryDto.getId())).collect(Collectors.toList());
        List<CategoryTreeDto> categoryTreeDtos = com.ms.common.utils.BeanUtils.copyList(children, CategoryTreeDto.class);
        categoryTreeDtos = categoryTreeDtos.stream().peek(categoryTreeDto1 -> categoryTreeDto1.setChildren(getCategoryChildren(categoryTreeDto1,source))).collect(Collectors.toList());
        return categoryTreeDtos;
    }
}
