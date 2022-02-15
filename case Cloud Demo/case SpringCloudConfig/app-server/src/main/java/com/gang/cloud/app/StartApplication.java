package com.gang.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Classname StartApplication
 * @Description TODO
 * @Date 2021/10/8
 * @Created by zengzg
 */
@SpringBootApplication
@EnableEurekaClient
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
