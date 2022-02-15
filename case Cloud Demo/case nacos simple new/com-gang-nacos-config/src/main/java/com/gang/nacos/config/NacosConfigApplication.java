package com.gang.nacos.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DiscoveryApplication
 * @Description TODO
 * @Date 2021/5/25
 * @Created by zengzg
 */
@SpringBootApplication
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

}
