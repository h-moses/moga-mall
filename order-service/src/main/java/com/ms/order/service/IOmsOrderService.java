package com.ms.order.service;

import com.ms.order.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.order.vo.OrderDetailVo;
import com.ms.order.vo.OrderSubmitResVo;
import com.ms.order.vo.OrderSubmitVo;

import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
public interface IOmsOrderService extends IService<OmsOrder> {

    OrderDetailVo queryOrderDetail() throws ExecutionException, InterruptedException;

    OrderSubmitResVo submitOrder(OrderSubmitVo orderVo);
}
