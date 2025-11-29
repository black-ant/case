package com.gang.web.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Spring MVC 高级功能示例应用
 * <p>
 * 本项目演示 Spring MVC 的各种高级功能，包括：
 * <ul>
 *     <li>Filter - 请求过滤器</li>
 *     <li>Interceptor - 拦截器</li>
 *     <li>Session - 会话管理</li>
 *     <li>Cookie - Cookie 操作</li>
 *     <li>Exception Handler - 统一异常处理</li>
 *     <li>Listener - 监听器</li>
 * </ul>
 * </p>
 * 
 * <h3>项目特点：</h3>
 * <ul>
 *     <li>@ServletComponentScan - 扫描 @WebFilter, @WebListener 等 Servlet 注解</li>
 *     <li>完整的 MVC 架构示例</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
