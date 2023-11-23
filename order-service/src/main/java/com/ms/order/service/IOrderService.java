package com.ms.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.common.to.SeckillOrderTo;
import com.ms.order.entity.OrderEntity;
import com.ms.order.vo.*;
import org.springframework.http.HttpRequest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
public interface IOrderService extends IService<OrderEntity> {

    OrderDetailVo queryOrderDetail() throws ExecutionException, InterruptedException;

    OrderSubmitResVo submitOrder(OrderSubmitVo orderVo);

    OrderEntity queryOrderStatus(String orderSn);

    void closeOrder(OrderEntity order);

    PayVo queryPayInfoByOrderSn(String orderSn);

    String handlePayResult(Map<String, String> parameters) throws IllegalAccessException;

    void createSeckillOrder(SeckillOrderTo seckillOrderTo);
}
