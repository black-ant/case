package com.gang.gateway.bean.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Simple re-route from: /get to: http://httpbin.org/80
                // And adds a simple "hello:world" HTTP Header
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .path("/blog")
                        .filters(f -> f.addRequestHeader(HttpHeaders.LOCATION, "http://www.antblack.xyz"))
                        .uri("http://www.antblack.xyz"))
//                .route(p -> p
//                        .path("/baidu")
//                        .filters(f-> PreserveHostHead)
//                        .uri("http://www.antblack.xyz"))
                .build();
    }
}
