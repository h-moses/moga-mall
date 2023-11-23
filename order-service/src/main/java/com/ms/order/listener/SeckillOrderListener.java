package com.ms.order.listener;

import com.ms.common.to.SeckillOrderTo;
import com.ms.order.configuration.RabbitConfiguration;
import com.ms.order.service.IOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@RabbitListener(queues = RabbitConfiguration.ORDER_SECKILL_QUEUE)
@Component
@Slf4j
public class SeckillOrderListener {

    @Resource
    IOrderService orderService;

    @RabbitHandler
    public void handleSeckillOrder(SeckillOrderTo seckillOrderTo, Channel channel, Message message) throws IOException {
        try {
            log.info("准备创建秒杀订单的详细信息");
            orderService.createSeckillOrder(seckillOrderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
