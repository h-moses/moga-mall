package com.ms.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
public class GatewayConfiguration {

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder().build();
    }
}
