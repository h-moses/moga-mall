package com.ms.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/api/user/login", "/api/user/register").permitAll()
                .anyExchange().authenticated()
                .and()
//                .addFilterAt(new JwtAuthenticationFilter(jwtSecret, jwtExpiration), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
