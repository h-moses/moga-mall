package com.ms.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SeckillServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SeckillServiceApp.class, args);
    }
}
