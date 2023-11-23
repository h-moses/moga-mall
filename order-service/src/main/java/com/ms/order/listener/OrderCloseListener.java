package com.ms.order.listener;

import com.ms.order.configuration.RabbitConfiguration;
import com.ms.order.entity.OrderEntity;
import com.ms.order.service.IOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@RabbitListener(queues = RabbitConfiguration.ORDER_CANCEL_QUEUE)
@Component
public class OrderCloseListener {

    @Resource
    IOrderService orderService;

    @RabbitHandler
    public void closeOrder(OrderEntity order, Channel channel, Message message) throws IOException {
        orderService.closeOrder(order);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
