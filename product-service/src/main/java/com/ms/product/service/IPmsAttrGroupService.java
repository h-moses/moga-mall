package com.ms.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.product.domain.entity.PmsAttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.vo.AttrGroupDetailVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsAttrGroupService extends IService<PmsAttrGroup> {

    Page<PmsAttrGroup> pageAttrGroup(Long categoryId, String keyword, Integer pageNum, Integer pageSize);

    List<AttrGroupDetailVo> getGroupDetail(Long categoryId);
}
