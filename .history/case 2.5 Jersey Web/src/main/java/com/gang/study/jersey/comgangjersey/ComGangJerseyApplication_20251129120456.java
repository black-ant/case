package com.gang.study.jersey.comgangjersey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot + Jersey 示例应用
 * <p>
 * Jersey 是 JAX-RS (Java API for RESTful Web Services) 的参考实现，
 * 由 Oracle/Eclipse 基金会维护。
 * </p>
 * 
 * <h3>Jersey 特点：</h3>
 * <ul>
 *     <li>JAX-RS 标准参考实现</li>
 *     <li>与 Spring Boot 良好集成</li>
 *     <li>支持依赖注入、过滤器、拦截器等</li>
 *     <li>内置 JSON/XML 序列化支持</li>
 * </ul>
 * 
 * <h3>使用场景：</h3>
 * <ul>
 *     <li>需要遵循 JAX-RS 标准的项目</li>
 *     <li>从 Java EE 迁移到 Spring Boot</li>
 *     <li>团队熟悉 JAX-RS 而非 Spring MVC</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class ComGangJerseyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangJerseyApplication.class, args);
    }
}
