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
 * 问候处理器
 * <p>
 * WebFlux 函数式端点的请求处理器。
 * 每个处理方法接收 ServerRequest，返回 Mono&lt;ServerResponse&gt;。
 * </p>
 * 
 * <h3>ServerRequest 常用方法：</h3>
 * <pre>
 * request.pathVariable("id")     - 获取路径参数
 * request.queryParam("name")     - 获取查询参数
 * request.bodyToMono(User.class) - 获取请求体
 * request.headers()              - 获取请求头
 * </pre>
 * 
 * <h3>ServerResponse 常用方法：</h3>
 * <pre>
 * ServerResponse.ok()            - 200 响应
 * ServerResponse.created(uri)    - 201 响应
 * ServerResponse.notFound()      - 404 响应
 * .body(Mono, Class)             - 设置响应体
 * .bodyValue(object)             - 设置响应体（简写）
 * </pre>
 *
 * @author zengzg
 * @since 2021/4/28
 */
@Component
public class GreetingHandler {

    private static final Logger logger = LoggerFactory.getLogger(GreetingHandler.class);

    /**
     * 问候接口处理方法
     * <p>
     * GET /hello
     * </p>
     *
     * @param request 服务器请求对象
     * @return Mono<ServerResponse> 异步响应
     */
    public Mono<ServerResponse> hello(ServerRequest request) {
        logger.info("Hello endpoint called");
        
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring WebFlux!"));
    }
}
