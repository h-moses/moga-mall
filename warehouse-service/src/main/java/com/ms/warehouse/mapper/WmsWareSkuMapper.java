package com.ms.warehouse.mapper;

import com.ms.warehouse.entity.WmsWareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
