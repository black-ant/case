package com.gang.cloud.template.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ComGangCloudTemplateAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangCloudTemplateAccountApplication.class, args);
    }

}
