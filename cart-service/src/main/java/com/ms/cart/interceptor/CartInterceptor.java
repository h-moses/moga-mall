package com.ms.cart.interceptor;

import com.ms.common.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CartInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<String> THREAD_LOCAL_CART = new ThreadLocal<>();

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final String[] WHITE_URL_LIST = {
            "/doc.html",
            "/favicon.ico",
            "/webjars/css/*",
            "/webjars/js/*",
            "/error",
            "/swagger-resources"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        log.info(requestURI);
        for (String allowedPath : WHITE_URL_LIST) {
            if (PATH_MATCHER.match(allowedPath, requestURI)) {
                return true;
            }
        }
        String username = request.getHeader(AuthConstant.USER_HEADER);
        if (StringUtils.hasText(username)) {
            THREAD_LOCAL_CART.set(username);
            return true;
        }
        log.warn("用户尚未认证，不可进入购物车");
        return false;
    }
}
