package com.gang.cloud.template.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker //启动Hystrix
public class ComGangCloudTemplateOrderApplication {

    /**
     * 调用地址 :
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ComGangCloudTemplateOrderApplication.class, args);
    }

}
