package com.example.springaidep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring AI Dependency Demo Application
 * 
 * 展示所有 Spring AI 官方依赖的使用示例
 * 基于 Spring AI 0.8.1 版本
 */
@SpringBootApplication
public class SpringAiDependencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiDependencyApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("Spring AI Dependency Demo 启动成功！");
        System.out.println("访问: http://localhost:8080/actuator/health");
        System.out.println("API 文档: http://localhost:8080/swagger-ui.html");
        System.out.println("==============================================\n");
    }
}
