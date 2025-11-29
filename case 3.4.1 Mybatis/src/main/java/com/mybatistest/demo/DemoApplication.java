package com.mybatistest.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyBatis 示例应用
 * <p>
 * 本项目演示 Spring Boot 集成 MyBatis 的完整用法：
 * <ul>
 *     <li>XML 映射方式</li>
 *     <li>注解映射方式</li>
 *     <li>MyBatis Generator 代码生成</li>
 *     <li>拦截器插件</li>
 * </ul>
 * </p>
 * 
 * <h3>MyBatis vs JPA：</h3>
 * <table>
 *     <tr><th>特性</th><th>MyBatis</th><th>JPA</th></tr>
 *     <tr><td>SQL 控制</td><td>完全控制</td><td>自动生成</td></tr>
 *     <tr><td>学习曲线</td><td>较低</td><td>较高</td></tr>
 *     <tr><td>适用场景</td><td>复杂 SQL</td><td>简单 CRUD</td></tr>
 *     <tr><td>性能优化</td><td>手动优化</td><td>依赖框架</td></tr>
 * </table>
 * 
 * <h3>前置条件：</h3>
 * <p>需要配置 MySQL 数据库并执行 mybatistest.sql 初始化脚本</p>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
@MapperScan("com.mybatistest.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
