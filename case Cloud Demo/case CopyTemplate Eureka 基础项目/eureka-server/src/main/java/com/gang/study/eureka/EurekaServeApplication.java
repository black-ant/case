package com.gang.study.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname EurekaServeApplication
 * @Description TODO
 * @Date 2021/3/7 14:04
 * @Created by zengzg
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServeApplication.class, args);
    }

}
