package com.ms.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ms"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Cloud Gateway Microservice")
                .description("API documentation for Spring Cloud Gateway Microservice")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/swagger-resources/**")
                        .filters(f -> f.rewritePath("/swagger-resources(?<segment>/?.*)", "/${segment}"))
                        .uri("lb://knife4j-spring-boot-starter"))
                .route(r -> r.path("/webjars/**")
                        .filters(f -> f.rewritePath("/webjars/(?<segment>.*?)", "/${segment}"))
                        .uri("lb://knife4j-spring-boot-starter"))
                .route(r -> r.path("/doc.html")
                        .uri("lb://knife4j-spring-boot-starter"))
                .build();
    }
}
