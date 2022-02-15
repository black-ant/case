package com.gang.cloud.template.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Classname ConfigApplication
 * @Description Config Server
 * @Date 2021/3/16 9:59
 * @Created by zengzg
 */
@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigApplication {


    /**
     * 请求地址 : http://127.0.0.1:1008/default/dev
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
