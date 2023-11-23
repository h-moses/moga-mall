package com.ms.warehouse.interceptor;

import com.ms.common.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前请求的 HttpServletRequest
        log.info("进入Feign拦截器....");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.warn(String.valueOf(attributes != null));
        assert attributes != null;
        HttpServletRequest servletRequest = attributes.getRequest();

        // 获取原请求的请求头信息
        String header = servletRequest.getHeader(AuthConstant.USER_HEADER);

        // 将原请求的请求头信息添加到新的接口的请求头中
        requestTemplate.header(AuthConstant.USER_HEADER, header);
    }
}
