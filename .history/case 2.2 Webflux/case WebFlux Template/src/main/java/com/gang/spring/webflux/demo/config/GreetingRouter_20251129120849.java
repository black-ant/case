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
 * WebFlux 路由配置
 * <p>
 * 使用函数式方式定义路由，替代传统的 @Controller + @RequestMapping 注解。
 * </p>
 * 
 * <h3>RouterFunction 组成部分：</h3>
 * <ul>
 *     <li>RequestPredicate - 请求匹配条件（路径、方法、请求头等）</li>
 *     <li>HandlerFunction - 请求处理函数</li>
 * </ul>
 * 
 * <h3>常用 RequestPredicates：</h3>
 * <pre>
 * GET("/path")              - GET 请求
 * POST("/path")             - POST 请求
 * accept(MediaType.JSON)    - Accept 头匹配
 * contentType(MediaType.JSON) - Content-Type 匹配
 * </pre>
 *
 * @author zengzg
 * @since 2021/4/28
 */
@Configuration
public class GreetingRouter {

    private static final Logger logger = LoggerFactory.getLogger(GreetingRouter.class);

    /**
     * 定义路由规则
     * <p>
     * GET /hello -> GreetingHandler.hello()
     * </p>
     *
     * @param greetingHandler 处理器
     * @return 路由函数
     */
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        logger.info("Registering route: GET /hello");
        
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/hello")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        greetingHandler::hello
                );
    }
}
