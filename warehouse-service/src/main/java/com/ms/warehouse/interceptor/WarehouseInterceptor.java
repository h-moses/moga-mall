package com.ms.warehouse.interceptor;

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
public class WarehouseInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<String> THREAD_LOCAL_STOCK = new ThreadLocal<>();

    private static final String[] WHITE_URL_LIST = {
            "/doc.html",
            "/favicon.ico",
            "/webjars/css/*",
            "/webjars/js/*",
            "/error",
            "/swagger-resources",
            "/warehouse/sku/stock/deduction"
    };

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info(requestURI);
        for (String allowedPath : WHITE_URL_LIST) {
            if (PATH_MATCHER.match(allowedPath, requestURI)) {
                return true;
            }
        }
        String claim = request.getHeader(AuthConstant.USER_HEADER);
        if (StringUtils.hasText(claim)) {
            THREAD_LOCAL_STOCK.set(claim);
            return true;
        }
        log.warn("用户尚未认证，不可进入库存系统");
        return false;
    }
}
