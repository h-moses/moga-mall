package com.ms.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.product.domain.entity.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Mapper
public interface PmsSkuSaleAttrValueMapper extends BaseMapper<PmsSkuSaleAttrValue> {

    List<String> querySaleAttr(@Param("skuId") Long skuId);

}
