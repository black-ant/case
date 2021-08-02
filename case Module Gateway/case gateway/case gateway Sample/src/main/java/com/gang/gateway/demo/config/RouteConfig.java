package com.gang.gateway.demo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RouteConfig
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@Configuration
public class RouteConfig {

    /**
     * 访问 http://localhost:8080/ csdn
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/csdn").uri("https://www.baidu.com"))
                .build();
    }
}
