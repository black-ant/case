package com.gang.study.freemarker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot + Freemarker 模板引擎示例应用
 * <p>
 * 本项目演示如何使用 Freemarker 作为视图模板引擎，包括：
 * <ul>
 *     <li>基础模板语法</li>
 *     <li>数据绑定与渲染</li>
 *     <li>模板继承与包含</li>
 *     <li>表单处理</li>
 * </ul>
 * </p>
 * 
 * <h3>访问地址：</h3>
 * <ul>
 *     <li>登录页：http://localhost:8080/login</li>
 *     <li>用户页：http://localhost:8080/user</li>
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
