package com.ms.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.order.entity.OrderItemEntity;
import com.ms.order.mapper.OmsOrderItemMapper;
import com.ms.order.service.IOmsOrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OrderItemEntity> implements IOmsOrderItemService {

}
