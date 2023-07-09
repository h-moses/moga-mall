package com.ms.product;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
public class ProductServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApp.class, args);
    }
}
