package com.gang.web.demo;

import com.gang.web.demo.to.ModuleTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SpringMVC Template 应用测试类
 *
 * @author zengzg
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文正确加载
    }

    @Test
    void testGetEndpoint() throws Exception {
        mockMvc.perform(get("/test/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    void testGetWithParams() throws Exception {
        mockMvc.perform(get("/test/getParam/myKey")
                        .param("name", "myName"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    void testPostBody() throws Exception {
        String json = "{\"name\":\"testModule\",\"description\":\"Test Description\"}";
        
        mockMvc.perform(post("/test/getBody")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testModule_processed"));
    }

    @Test
    void testCloudEndpoint() throws Exception {
        mockMvc.perform(get("/test/cloud"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cloud service is running"));
    }
}
