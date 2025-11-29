package com.gang.base;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Boot 应用集成测试
 *
 * @author zengzg
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BaseApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文正确加载
        assertNotNull(restTemplate);
    }

    @Test
    void testHealthCheck() {
        String result = restTemplate.getForObject("/test", String.class);
        assertEquals("OK", result);
    }

    @Test
    void testUserGetTest() {
        String result = restTemplate.getForObject("/user/get/test", String.class);
        assertEquals("test", result);
    }

    @Test
    void testPathVariable() {
        String result = restTemplate.getForObject("/user/get/path/hello", String.class);
        assertEquals("hello", result);
    }

    @Test
    void testQueryParam() {
        String result = restTemplate.getForObject("/user/get/param?key=world", String.class);
        assertEquals("world", result);
    }
}
