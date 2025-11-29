package com.gang.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 基础应用启动类
 * <p>
 * 这是一个演示 Spring Boot 基本功能的示例项目，包含：
 * <ul>
 *     <li>RESTful API 基础用法（GET、POST）</li>
 *     <li>路径参数、查询参数、请求体的处理</li>
 *     <li>JSON 序列化与反序列化</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class BaseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
