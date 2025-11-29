package com.gang.spring.webflux.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * WebFlux 应用测试类
 *
 * @author zengzg
 */
@SpringBootTest
@AutoConfigureWebTestClient
class DemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文正确加载
    }

    @Test
    void testHelloEndpoint() {
        webTestClient.get()
                .uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Hello, Spring WebFlux!");
    }
}
