package com.gang.study.swaggerv3.demo;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.ApplicationPath;

/**
 * swagger 使用 ,访问地址 : http://localhost:8087/swagger/index.html
 * 1 之前 maven-resources-plugin 中 exclude index 导致 index 未生成
 * 2 注意 V3 需要进行编译处理
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public OpenApiResource openApiResource() {
        return new OpenApiResource();
    }

}
