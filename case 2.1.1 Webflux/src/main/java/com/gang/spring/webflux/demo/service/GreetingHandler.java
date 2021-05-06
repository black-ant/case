package com.gang.spring.webflux.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Classname GreetingHandler
 * @Description TODO
 * @Date 2021/4/28
 * @Created by zengzg
 */
@Component
public class GreetingHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Mono<ServerResponse> hello(ServerRequest request) {
        logger.info("------> [Hello 接口被调用] <-------");
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring!"));
    }
}
