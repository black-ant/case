package com.gang.webflux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring WebFlux Mono/Flux 示例应用
 * <p>
 * 本项目演示 Project Reactor 的核心概念：
 * <ul>
 *     <li>Mono - 0 或 1 个元素的异步序列</li>
 *     <li>Flux - 0 到 N 个元素的异步序列</li>
 *     <li>响应式操作符（map, flatMap, filter, transform 等）</li>
 * </ul>
 * </p>
 * 
 * <h3>响应式编程核心概念：</h3>
 * <ul>
 *     <li>非阻塞 - 不会阻塞线程等待结果</li>
 *     <li>背压 - 消费者可以控制生产者的速度</li>
 *     <li>声明式 - 描述数据流而不是具体步骤</li>
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
