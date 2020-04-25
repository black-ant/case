package com.gang.study.sofabbot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname SofaApplication
 * @Description TODO
 * @Date 2020/4/25 21:24
 * @Created by zengzg
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.gang"})
public class SofaApplication {
    public static void main(String[] args) {

        SpringApplication.run(SofaApplication.class, args);
    }
}
