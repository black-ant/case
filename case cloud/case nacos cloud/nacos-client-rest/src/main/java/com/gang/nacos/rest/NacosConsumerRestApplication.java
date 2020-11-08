package com.gang.nacos.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname NacosConsumerRestApplication
 * @Description TODO
 * @Date 2020/11/8 11:32
 * @Created by zengzg
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerRestApplication {


    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerRestApplication.class, args);
    }
}
