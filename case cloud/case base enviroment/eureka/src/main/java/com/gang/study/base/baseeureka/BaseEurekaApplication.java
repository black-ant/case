package com.gang.study.base.baseeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BaseEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseEurekaApplication.class, args);
    }

}
