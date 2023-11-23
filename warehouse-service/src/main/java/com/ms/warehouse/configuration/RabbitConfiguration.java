package com.ms.warehouse.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfiguration {

    public static final String STOCK_EXCHANGE = "stock-event-exchange";
    public static final String STOCK_RELEASE_QUEUE = "stock-release-queue";

    public static final String STOCK_DELAY_QUEUE = "stock-delay-queue";

    public static final String DEAD_ROUTING_KEY = "stock.event.release";

    public static final String LOCK_ROUTING_KEY = "stock.event.lock";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange stockExchange() {
        return new TopicExchange(STOCK_EXCHANGE, true, false);
    }

    @Bean
    public Queue releaseStockQueue() {
        return new Queue(STOCK_RELEASE_QUEUE, true, false, false);
    }

    @Bean
    public Queue delayStockQueue() {
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-dead-letter-exchange", STOCK_EXCHANGE);
        arg.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        arg.put("x-message-ttl", 1200000);
        return new Queue(STOCK_DELAY_QUEUE, true, false, false, arg);
    }

    @Bean
    public Binding releaseQueueBinding(TopicExchange stockExchange, Queue releaseStockQueue) {
        return BindingBuilder.bind(releaseStockQueue).to(stockExchange).with(DEAD_ROUTING_KEY);
    }

    @Bean
    public Binding lockQueueBinding(TopicExchange stockExchange, Queue delayStockQueue) {
        return BindingBuilder.bind(delayStockQueue).to(stockExchange).with(LOCK_ROUTING_KEY);
    }
}
