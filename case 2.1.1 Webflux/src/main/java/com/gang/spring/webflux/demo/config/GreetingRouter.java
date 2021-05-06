package com.gang.spring.webflux.demo.config;

import com.gang.spring.webflux.demo.service.GreetingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Classname GreetingRouter
 * @Description TODO
 * @Date 2021/4/28
 * @Created by zengzg
 */
@Configuration
public class GreetingRouter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        logger.info("------> [注册 Hello 接口] <-------");
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
    }
}
