package com.gang.study.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TemplateResolve 单元测试
 *
 * @author zengzg
 */
class TemplateResolveTest {

    @Test
    void testResolveWithValues() {
        String template = "category:${}:product:${}";
        String result = TemplateResolve.resolve(template, "1", "2");
        assertEquals("category:1:product:2", result);
    }

    @Test
    void testResolveNoPlaceholder() {
        String template = "Hello World";
        String result = TemplateResolve.resolve(template, "unused");
        assertEquals("Hello World", result);
    }

    @Test
    void testResolveByMap() {
        String template = "Hello, ${name}! You are ${age} years old.";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "张三");
        params.put("age", 25);
        
        String result = TemplateResolve.resolveByMap(template, params);
        assertEquals("Hello, 张三! You are 25 years old.", result);
    }

    @Test
    void testResolveByProperties() {
        String template = "Server: ${host}:${port}";
        Properties props = new Properties();
        props.setProperty("host", "localhost");
        props.setProperty("port", "8080");
        
        String result = TemplateResolve.resolveByProperties(template, props);
        assertEquals("Server: localhost:8080", result);
    }

    @Test
    void testResolveByRule() {
        String template = "Value: ${key1}, ${key2}";
        String result = TemplateResolve.resolveByRule(template, key -> key.toUpperCase());
        assertEquals("Value: KEY1, KEY2", result);
    }

    @Test
    void testJexlResolve() {
        String template = "Hello, ${name}!";
        String result = TemplateResolve.jexlResolve(template, "name", "World");
        assertEquals("Hello, World!", result);
    }

    @Test
    void testJexlResolveWithJson() {
        String template = "User: ${name}, Age: ${age}";
        String json = "{\"name\":\"李四\",\"age\":30}";
        String result = TemplateResolve.jexlResolve(template, json);
        assertEquals("User: 李四, Age: 30", result);
    }

    @Test
    void testResolveWithObjectArray() {
        String template = "ID: ${}, Name: ${}";
        Object[] values = {123, "Test"};
        String result = TemplateResolve.resolve(template, values);
        assertEquals("ID: 123, Name: Test", result);
    }
}

