package com.gamg.easyrest.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RESTEasy (JAX-RS) 示例应用
 * <p>
 * 本项目演示如何在 Spring Boot 中集成 RESTEasy 实现 JAX-RS 规范：
 * <ul>
 *     <li>JAX-RS 注解（@Path, @GET, @POST 等）</li>
 *     <li>资源类定义</li>
 *     <li>请求/响应处理</li>
 * </ul>
 * </p>
 * 
 * <h3>JAX-RS vs Spring MVC：</h3>
 * <table>
 *     <tr><th>特性</th><th>JAX-RS</th><th>Spring MVC</th></tr>
 *     <tr><td>规范</td><td>Java EE 标准</td><td>Spring 专有</td></tr>
 *     <tr><td>路径注解</td><td>@Path</td><td>@RequestMapping</td></tr>
 *     <tr><td>方法注解</td><td>@GET, @POST</td><td>@GetMapping, @PostMapping</td></tr>
 *     <tr><td>实现</td><td>Jersey, RESTEasy</td><td>Spring MVC</td></tr>
 * </table>
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
