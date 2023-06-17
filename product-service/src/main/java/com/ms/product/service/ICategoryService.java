package com.ms.product.service;

import com.ms.product.dto.CategoryTreeDto;
import com.ms.product.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
public interface ICategoryService extends IService<Category> {

    List<CategoryTreeDto> getTreeList();
}
