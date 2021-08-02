package com.gang.cloud.zookeeper.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname RestConfig
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@Configuration
public class RestConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @LoadBalanced
    RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }
}
