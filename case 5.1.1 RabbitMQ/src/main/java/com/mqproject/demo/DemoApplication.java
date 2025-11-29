package com.mqproject.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RabbitMQ 消息队列示例应用
 * <p>
 * 本项目演示 Spring Boot 集成 RabbitMQ 的完整用法：
 * <ul>
 *     <li>消息生产者（Producer）</li>
 *     <li>消息消费者（Consumer）</li>
 *     <li>交换机类型（Direct, Topic, Fanout, Headers）</li>
 *     <li>消息确认机制</li>
 * </ul>
 * </p>
 * 
 * <h3>RabbitMQ 核心概念：</h3>
 * <ul>
 *     <li>Producer - 消息生产者</li>
 *     <li>Consumer - 消息消费者</li>
 *     <li>Queue - 消息队列</li>
 *     <li>Exchange - 交换机（路由消息）</li>
 *     <li>Binding - 绑定（Exchange 与 Queue 的关系）</li>
 *     <li>Routing Key - 路由键</li>
 * </ul>
 * 
 * <h3>前置条件：</h3>
 * <p>需要启动 RabbitMQ 服务器（默认 localhost:5672）</p>
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
