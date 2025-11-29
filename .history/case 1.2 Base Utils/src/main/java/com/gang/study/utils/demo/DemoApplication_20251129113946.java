package com.gang.study.utils.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基础工具类示例应用
 * <p>
 * 演示常用工具类的封装和使用，包括：
 * <ul>
 *     <li>系统信息获取（操作系统、JVM 等）</li>
 *     <li>文件操作工具</li>
 *     <li>命令执行工具</li>
 * </ul>
 * </p>
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
