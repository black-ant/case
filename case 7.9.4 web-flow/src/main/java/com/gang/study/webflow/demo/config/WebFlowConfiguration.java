package com.gang.study.webflow.demo.config;

import com.gang.study.webflow.demo.entity.ServerResponse;
import com.gang.study.webflow.demo.logic.XttblogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * @Classname WebFlowConfiguration
 * @Description TODO
 * @Date 2020/6/4 16:36
 * @Created by zengzg
 */
@Configuration
public class WebFlowConfiguration {

    @Bean
    public RouterFunction<ServerResponse> helloXttblog(XttblogHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        handler::helloXttblog);
    }
}
