package com.gang.redis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Data Redis 示例应用
 * <p>
 * 本项目演示 Spring Boot 集成 Redis 的基本用法：
 * <ul>
 *     <li>String 类型操作</li>
 *     <li>Hash 类型操作</li>
 *     <li>List 类型操作</li>
 *     <li>Set 类型操作</li>
 *     <li>ZSet 有序集合操作</li>
 * </ul>
 * </p>
 * 
 * <h3>Redis 数据类型：</h3>
 * <table>
 *     <tr><th>类型</th><th>说明</th><th>使用场景</th></tr>
 *     <tr><td>String</td><td>字符串</td><td>缓存、计数器</td></tr>
 *     <tr><td>Hash</td><td>哈希表</td><td>对象存储</td></tr>
 *     <tr><td>List</td><td>列表</td><td>消息队列</td></tr>
 *     <tr><td>Set</td><td>集合</td><td>去重、交并集</td></tr>
 *     <tr><td>ZSet</td><td>有序集合</td><td>排行榜</td></tr>
 * </table>
 * 
 * <h3>前置条件：</h3>
 * <p>需要启动 Redis 服务器（默认 localhost:6379）</p>
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
