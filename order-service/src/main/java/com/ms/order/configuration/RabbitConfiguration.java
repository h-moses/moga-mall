package com.ms.order.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String ORDER_CANCEL_QUEUE = "order-cancel-queue";

    public static final String ORDER_DELAY_QUEUE = "order-delay-queue";

    public static final String ORDER_SECKILL_QUEUE = "order-seckill-queue";

    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";

    public static final String CANCEL_ROUTING_KEY = "order.event.cancel";

    public static final String CREATE_ROUTING_KEY = "order.event.create";

    public static final String SECKILL_ROUTING_KEY = "order.event.seckill";

    public static final Integer MESSAGE_TIME_OUT = 600000;

    @Bean
    public TopicExchange orderEventExchange() {
//        Map<String, Object> argument = new HashMap<>();
//        argument.put("x-delayed-type", "direct");
        return new TopicExchange(ORDER_EVENT_EXCHANGE, true, false, null);
    }

    @Bean
    public Queue cancelOrderQueue() {
        return new Queue(ORDER_CANCEL_QUEUE);
    }

    @Bean
    public Queue seckillOrderQueue() {
        return new Queue(ORDER_SECKILL_QUEUE, true, false, false);
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
    public Binding cancelQueueBinding(TopicExchange orderEventExchange, Queue cancelOrderQueue) {
        return BindingBuilder.bind(cancelOrderQueue).to(orderEventExchange).with(CANCEL_ROUTING_KEY);
    }

    @Bean
    public Binding createQueueBinding(TopicExchange orderEventExchange, Queue delayOrderQueue) {
        return BindingBuilder.bind(delayOrderQueue).to(orderEventExchange).with(CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding seckillQueueBinding(TopicExchange orderEventExchange, Queue seckillOrderQueue) {
        return BindingBuilder.bind(seckillOrderQueue).to(orderEventExchange).with(SECKILL_ROUTING_KEY);
    }

    @Bean
    public Binding stockQueueBinding() {
        return new Binding("stock-release-queue", Binding.DestinationType.QUEUE, ORDER_EVENT_EXCHANGE, CANCEL_ROUTING_KEY, null);
    }

}
