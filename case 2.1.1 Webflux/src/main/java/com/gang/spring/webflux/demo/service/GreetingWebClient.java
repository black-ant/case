package com.gang.spring.webflux.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Classname GreetingWebClient
 * @Description TODO
 * @Date 2021/4/28
 * @Created by zengzg
 */
public class GreetingWebClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.get()
            .uri("/hello")
            .accept(MediaType.TEXT_PLAIN)
            .exchange();

    public String getResult() {
        logger.info("------> [调用 Hello 接口] <-------");
        return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
    }
}
