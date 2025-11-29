package com.fileupload.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件上传下载示例应用
 * <p>
 * 本项目演示 Spring Boot 中的文件处理：
 * <ul>
 *     <li>文件上传（单文件、多文件）</li>
 *     <li>文件下载</li>
 *     <li>上传进度监控</li>
 *     <li>文件类型和大小限制</li>
 * </ul>
 * </p>
 * 
 * <h3>配置项：</h3>
 * <pre>
 * # 单个文件最大大小
 * spring.servlet.multipart.max-file-size=10MB
 * # 请求最大大小
 * spring.servlet.multipart.max-request-size=100MB
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
