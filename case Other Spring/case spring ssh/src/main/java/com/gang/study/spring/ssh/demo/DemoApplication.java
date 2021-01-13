package com.gang.study.spring.ssh.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 部署流程
 *  Step 1 : 生成证书 , 放入 resource 目录下
 *  Step 2 : 配置 Application.properties
 *  Step 3 : 添加配置类
 *  Step 4 : 此时提示的仍然是不安全 , 需要导入证书
 *
 * 使用流程
 *  HTTP 访问地址 : http://127.0.0.1:8088/test/hello
 *  HTTPS 访问路径 : https://127.0.0.1:8086/test/hello
 *
 *  从浏览器下载证书 , windows 输入 mmc , 在证书管理单元将证书添加到受信任的根证书
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
