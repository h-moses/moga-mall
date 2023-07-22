package com.ms.product.service;

import com.ms.product.dto.CategoryTreeDto;
import com.ms.product.entity.PmsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.vo.CategoryParamVo;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsCategoryService extends IService<PmsCategory> {

    void updateCategory(CategoryParamVo categoryParamVo);

    List<CategoryTreeDto> getTreeList();

    boolean deleteByIds(Integer[] ids);
}
