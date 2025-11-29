package com.gang.web.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AutoRestApi 应用测试类
 *
 * @author zengzg
 */
@SpringBootTest
@AutoConfigureMockMvc
class AutoRestApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文正确加载
    }

    @Test
    void testDynamicApi() throws Exception {
        // 测试动态注册的 API
        mockMvc.perform(get("/test/hello/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from dynamically registered API!"));
    }
}

