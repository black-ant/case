package com.gang.study.h2base.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * H2 内存数据库示例应用
 * <p>
 * 本项目演示 H2 数据库的使用，包括：
 * <ul>
 *     <li>内存模式 - 数据存储在内存中，应用关闭后数据丢失</li>
 *     <li>客户端模式 - 连接外部 H2 服务器</li>
 *     <li>嵌入式模式 - 数据持久化到文件</li>
 * </ul>
 * </p>
 * 
 * <h3>H2 数据库特点：</h3>
 * <ul>
 *     <li>纯 Java 实现，无需安装</li>
 *     <li>支持多种模式（内存、嵌入式、服务器）</li>
 *     <li>兼容大部分 SQL 标准</li>
 *     <li>适合开发测试和小型应用</li>
 * </ul>
 * 
 * <h3>连接字符串格式：</h3>
 * <pre>
 * 内存模式：jdbc:h2:mem:dbname
 * 嵌入式：jdbc:h2:~/dbname
 * TCP 模式：jdbc:h2:tcp://localhost/~/dbname
 * </pre>
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
