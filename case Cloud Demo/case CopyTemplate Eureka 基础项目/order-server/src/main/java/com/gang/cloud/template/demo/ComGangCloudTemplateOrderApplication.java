package com.gang.cloud.template.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.clientconfig.OkHttpFeignConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(value = {OkHttpFeignConfiguration.class})
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
