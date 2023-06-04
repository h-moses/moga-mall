package com.ms.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}
