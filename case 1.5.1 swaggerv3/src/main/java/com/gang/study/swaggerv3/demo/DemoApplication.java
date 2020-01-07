package com.gang.study.swaggerv3.demo;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.ApplicationPath;

/**
 * swagger 使用 ,访问地址
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //    @Bean
    //    public OpenApiResource openApiResource() {
    //        return new OpenApiResource();
    //    }

}
