package com.mythymeleaf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot + Thymeleaf 模板引擎示例应用
 * <p>
 * 本项目演示如何使用 Thymeleaf 作为视图模板引擎，包括：
 * <ul>
 *     <li>Thymeleaf 基础语法</li>
 *     <li>表单处理与数据绑定</li>
 *     <li>模板片段与布局</li>
 *     <li>国际化支持</li>
 * </ul>
 * </p>
 * 
 * <h3>Thymeleaf 特点：</h3>
 * <ul>
 *     <li>自然模板 - HTML 文件可直接在浏览器打开预览</li>
 *     <li>Spring 集成 - 与 Spring MVC 无缝集成</li>
 *     <li>表达式语言 - 支持 OGNL 和 SpringEL</li>
 * </ul>
 * 
 * <h3>访问地址：</h3>
 * <ul>
 *     <li>首页：http://localhost:8080/</li>
 *     <li>登录：http://localhost:8080/login</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
