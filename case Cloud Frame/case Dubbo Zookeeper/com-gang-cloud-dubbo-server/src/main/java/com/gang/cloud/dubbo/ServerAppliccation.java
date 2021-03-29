package com.gang.cloud.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname ServerAppliccation
 * @Description TODO
 * @Date 2020/9/29 23:20
 * @Created by zengzg
 */

@SpringBootApplication
@EnableDubbo
public class ServerAppliccation {


    public static void main(String[] args) {
        SpringApplication.run(ServerAppliccation.class, args);
    }
}
