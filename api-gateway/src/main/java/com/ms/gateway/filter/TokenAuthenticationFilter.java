package com.ms.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TokenAuthenticationFilter implements GatewayFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            token = token.substring(BEARER_PREFIX.length());
            // 进行Token验证
            if (isValidToken(token)) {
                // 验证通过，将Token信息存储到请求上下文中
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header(AUTHORIZATION_HEADER, BEARER_PREFIX + token)
                        .build();
                ServerWebExchange newExchange = exchange.mutate().request(request).build();
                return chain.filter(newExchange);
            }
        }
        // 验证失败，返回401错误
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private boolean isValidToken(String token) {
        return false;
    }
}
