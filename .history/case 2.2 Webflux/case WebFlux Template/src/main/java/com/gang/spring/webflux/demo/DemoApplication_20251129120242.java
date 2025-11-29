package com.gang.spring.webflux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring WebFlux 函数式端点示例应用
 * <p>
 * 本项目演示 WebFlux 的函数式编程模型，包括：
 * <ul>
 *     <li>RouterFunction - 路由定义</li>
 *     <li>HandlerFunction - 请求处理器</li>
 *     <li>ServerRequest / ServerResponse - 请求响应对象</li>
 * </ul>
 * </p>
 * 
 * <h3>两种编程模型对比：</h3>
 * <table>
 *     <tr><th>模型</th><th>注解式</th><th>函数式</th></tr>
 *     <tr><td>路由定义</td><td>@RequestMapping</td><td>RouterFunction</td></tr>
 *     <tr><td>处理器</td><td>@Controller 方法</td><td>HandlerFunction</td></tr>
 *     <tr><td>优点</td><td>熟悉、简洁</td><td>函数式、可组合</td></tr>
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
