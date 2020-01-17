package com.gang.study.cloud.nacos.gangcloudnacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class GangCloudNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GangCloudNacosApplication.class, args);
    }
}
