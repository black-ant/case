package com.alibaba.nacos.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname DiscoveryApplication
 * @Description TODO
 * @Date 2021/5/25
 * @Created by zengzg
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class UserDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDiscoveryApplication.class, args);
    }

}
