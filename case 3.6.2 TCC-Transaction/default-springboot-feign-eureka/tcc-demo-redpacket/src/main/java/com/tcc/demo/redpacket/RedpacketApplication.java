package com.tcc.demo.redpacket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.tcc", "io.prong"})
@EnableDiscoveryClient
@EnableFeignClients("com.tcc")
public class RedpacketApplication {

	public static void main(String[] args) {
        SpringApplication.run(RedpacketApplication.class, args);
    }
}
