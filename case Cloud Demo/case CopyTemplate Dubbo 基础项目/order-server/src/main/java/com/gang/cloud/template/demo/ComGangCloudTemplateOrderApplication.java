package com.gang.cloud.template.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ComGangCloudTemplateOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangCloudTemplateOrderApplication.class, args);
    }

}
