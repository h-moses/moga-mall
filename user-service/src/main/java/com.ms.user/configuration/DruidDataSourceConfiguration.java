package com.ms.user.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(value = "spring.datasource.druid")
public class DruidDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
