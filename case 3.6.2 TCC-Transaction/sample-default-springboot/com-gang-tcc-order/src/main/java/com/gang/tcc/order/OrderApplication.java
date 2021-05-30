package com.gang.tcc.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @Classname OrderApplication
 * @Description TODO
 * @Date 2021/5/28
 * @Created by zengzg
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo             //启动dubbo配置与注解
@EnableAspectJAutoProxy  //启动切面类
@ImportResource(locations = {"classpath:tcc-transaction.xml", "classpath:tcc-transaction-dubbo.xml"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
