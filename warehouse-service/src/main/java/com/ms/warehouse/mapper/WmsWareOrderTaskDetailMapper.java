package com.ms.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.warehouse.domain.entity.WareOrderTaskDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库存工作单 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Mapper
public interface WmsWareOrderTaskDetailMapper extends BaseMapper<WareOrderTaskDetailEntity> {

}
