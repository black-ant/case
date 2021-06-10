package com.gang.cloud.template.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ComGangCloudTemplateOrderApplication {

    /**
     * 调用地址 :
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ComGangCloudTemplateOrderApplication.class, args);
    }

}
