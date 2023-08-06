package com.ms.product.mapper;

import com.ms.product.domain.entity.PmsSpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * spu信息 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Mapper
public interface PmsSpuInfoMapper extends BaseMapper<PmsSpuInfo> {

    void updateStatus(@Param("spuId") Long spuId,@Param("status") int productStatus);
}
