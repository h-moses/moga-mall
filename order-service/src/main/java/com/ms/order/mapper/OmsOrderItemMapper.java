package com.ms.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.order.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单项信息 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Mapper
public interface OmsOrderItemMapper extends BaseMapper<OrderItemEntity> {

}
