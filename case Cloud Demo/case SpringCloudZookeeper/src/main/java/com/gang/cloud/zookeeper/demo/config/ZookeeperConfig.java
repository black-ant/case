package com.gang.cloud.zookeeper.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ZookeeperConfig
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
public class ZookeeperConfig {

}
