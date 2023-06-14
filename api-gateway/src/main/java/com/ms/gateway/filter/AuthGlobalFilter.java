package com.ms.gateway.filter;

import com.ms.common.constant.AuthConstant;
import com.ms.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Order(0)
@Slf4j
public class AuthGlobalFilter implements GlobalFilter {

    private static final List<String> IGNORE_URLS = Arrays.asList(
            "/api/user/register",
            "/api/user/login"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (IGNORE_URLS.contains(path)) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_HEADER);
        if (!StringUtils.hasText(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String realToken = token.replace(AuthConstant.JWT_PREFIX, "");
        if (JwtUtils.validateToken(realToken)) {
            String username = JwtUtils.getSubjectFromToken(realToken);
            // token合法，从中取出用户信息，交由用户服务
            exchange.getRequest().mutate().header(AuthConstant.USER_HEADER, username);
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
