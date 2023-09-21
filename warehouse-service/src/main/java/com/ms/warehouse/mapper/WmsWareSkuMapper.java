package com.ms.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.warehouse.domain.entity.WmsWareSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品库存 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Mapper
public interface WmsWareSkuMapper extends BaseMapper<WmsWareSku> {

    long getStock(@Param("skuId") Long skuId);

    List<Long> listWareId(@Param("skuId") Long skuId);

    Integer lockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("quantity") Integer quantity);
}
