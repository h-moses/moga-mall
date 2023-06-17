package com.ms.product.service.impl;

import com.ms.common.utils.BeanUtils;
import com.ms.product.dto.CategoryTreeDto;
import com.ms.product.entity.Category;
import com.ms.product.mapper.CategoryMapper;
import com.ms.product.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<CategoryTreeDto> getTreeList() {
        // 查询所有分类
        List<Category> categoryList = list();
        
        // 找出一级分类
        List<Category> primary = categoryList.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());

        List<CategoryTreeDto> primaryDto = BeanUtils.copyList(primary, CategoryTreeDto.class);
        primaryDto.forEach(categoryTreeDto -> categoryTreeDto.setChildren(getCategoryChildren(categoryTreeDto, categoryList)));

        return primaryDto;
    }

    private List<CategoryTreeDto> getCategoryChildren(CategoryTreeDto primaryDto, List<Category> source) {
        List<Category> children = source.stream().filter(category -> category.getParentId().equals(primaryDto.getId())).collect(Collectors.toList());
        List<CategoryTreeDto> categoryTreeDtos = BeanUtils.copyList(children, CategoryTreeDto.class);
        categoryTreeDtos = categoryTreeDtos.stream().peek(categoryTreeDto1 -> categoryTreeDto1.setChildren(getCategoryChildren(categoryTreeDto1,source))).collect(Collectors.toList());
        return categoryTreeDtos;
    }
}
