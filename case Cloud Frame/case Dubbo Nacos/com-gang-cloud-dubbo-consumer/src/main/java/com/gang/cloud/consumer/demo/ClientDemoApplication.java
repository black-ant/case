package com.gang.cloud.consumer.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class ClientDemoApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.gang.cloud")
    @PropertySource("classpath:/application.properties")
    @ComponentScan(value = {"com.gang.cloud.consumer.demo"})
    static class ConsumerConfiguration {
    }

}
