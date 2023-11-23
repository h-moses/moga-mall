package com.ms.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class, args);
    }

}
