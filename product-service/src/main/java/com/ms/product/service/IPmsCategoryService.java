package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.product.domain.dto.CategoryTreeDto;
import com.ms.product.domain.entity.PmsCategory;
import com.ms.product.domain.vo.CategoryParamVo;

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

    List<CategoryTreeDto> getTreeList() throws JsonProcessingException;

    boolean deleteByIds(Integer[] ids);
}
