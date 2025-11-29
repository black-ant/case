package com.gang.study.hikari.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HikariCP 连接池示例应用
 * <p>
 * 本项目演示 Spring Boot 默认连接池 HikariCP 的配置和使用。
 * </p>
 * 
 * <h3>什么是 HikariCP？</h3>
 * <p>
 * HikariCP 是一个高性能 JDBC 连接池，具有以下特点：
 * <ul>
 *     <li>极快的启动速度</li>
 *     <li>轻量级（约 130KB）</li>
 *     <li>零开销</li>
 *     <li>可靠的连接管理</li>
 * </ul>
 * </p>
 * 
 * <h3>连接池对比：</h3>
 * <table>
 *     <tr><th>连接池</th><th>性能</th><th>功能</th><th>推荐场景</th></tr>
 *     <tr><td>HikariCP</td><td>最快</td><td>基础</td><td>高并发、追求性能</td></tr>
 *     <tr><td>Druid</td><td>快</td><td>监控丰富</td><td>需要SQL监控</td></tr>
 *     <tr><td>C3P0</td><td>较慢</td><td>成熟稳定</td><td>老项目兼容</td></tr>
 * </table>
 * 
 * <h3>Spring Boot 2.x 默认使用 HikariCP</h3>
 * <p>无需额外配置，引入 spring-boot-starter-data-jpa 即可。</p>
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
