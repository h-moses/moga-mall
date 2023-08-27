package com.ms.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.product.domain.entity.PmsAttrAttrgroupRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性&属性分组关联 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Mapper
public interface PmsAttrAttrgroupRelationMapper extends BaseMapper<PmsAttrAttrgroupRelation> {

    void batchDeleteRelation(@Param("relations") List<PmsAttrAttrgroupRelation> relations);
}
