package com.ms.order.configuration;

import com.ms.order.entity.OmsOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String ORDER_CANCEL_QUEUE = "order-cancel-queue";

    public static final String ORDER_DELAY_QUEUE = "order-delay-queue";

    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";

    public static final String CANCEL_ROUTING_KEY = "order.event.cancel";

    public static final String CREATE_ROUTING_KEY = "order.event.create";

    public static final Integer MESSAGE_TIME_OUT = 60000;

    @Bean
    public CustomExchange orderEventExchange() {
        Map<String, Object> argument = new HashMap<>();
        argument.put("x-delayed-type", "direct");
        return new CustomExchange(ORDER_EVENT_EXCHANGE, "x-delayed-message", true, false, argument);
    }

    @Bean
    public Queue cancelOrderQueue() {
        return new Queue(ORDER_CANCEL_QUEUE);
    }

    @Bean
    public Queue delayOrderQueue() {
        Map<String, Object> argument = new HashMap<>();
        argument.put("x-dead-letter-exchange", ORDER_EVENT_EXCHANGE);
        argument.put("x-dead-letter-routing-key", CANCEL_ROUTING_KEY);
        argument.put("x-message-ttl", MESSAGE_TIME_OUT);
        return new Queue(ORDER_DELAY_QUEUE, true, false, false, argument);
    }

    @Bean
    public Binding cancelQueueBinding(CustomExchange orderEventExchange, Queue cancelOrderQueue) {
        return BindingBuilder.bind(cancelOrderQueue).to(orderEventExchange).with(CANCEL_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding createQueueBinding(CustomExchange orderEventExchange, Queue delayOrderQueue) {
        return BindingBuilder.bind(delayOrderQueue).to(orderEventExchange).with(CREATE_ROUTING_KEY).noargs();
    }

    @RabbitListener(queues = ORDER_CANCEL_QUEUE)
    public void handleCancelOrderEvent(OmsOrder order) {
        log.info("收到取消订单的消息，订单编号：{}", order.getOrderSn());
    }
}
