package com.gang.base.controller;

import com.gang.base.common.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserController 单元测试
 *
 * @author zengzg
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateUser() {
        User user = User.builder()
                .username("张三")
                .age(25)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        User result = restTemplate.postForObject("/user/post/create", request, User.class);
        
        assertNotNull(result);
        assertEquals("张三", result.getUsername());
        assertEquals(19, result.getAge()); // 接口会将年龄设置为19
    }

    @Test
    void testPathVariableWithDifferentValues() {
        String[] testValues = {"test1", "test2", "中文测试"};
        
        for (String value : testValues) {
            String result = restTemplate.getForObject("/user/get/path/" + value, String.class);
            assertEquals(value, result);
        }
    }
}

