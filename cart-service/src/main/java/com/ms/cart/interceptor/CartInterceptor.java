package com.ms.cart.interceptor;

import com.ms.common.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CartInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<String> THREAD_LOCAL_CART = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = request.getHeader(AuthConstant.USER_HEADER);
        if (StringUtils.hasText(username)) {
            THREAD_LOCAL_CART.set(username);
            return true;
        }
        log.warn("用户尚未认证，不可进入购物车");
        return false;
    }
}
