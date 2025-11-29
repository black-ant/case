package com.gang.study.jersey.comgangjersey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Jersey 应用测试类
 *
 * @author zengzg
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ComGangJerseyApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文正确加载
    }

    @Test
    void testHealthEndpoint() {
        String result = restTemplate.getForObject("/rest/start/health", String.class);
        assertEquals("Jersey is running!", result);
    }

    @Test
    void testCityEndpoint() {
        String result = restTemplate.getForObject("/rest/start/city", String.class);
        assertNotNull(result);
        assertTrue(result.contains("Beijing"));
    }
}
