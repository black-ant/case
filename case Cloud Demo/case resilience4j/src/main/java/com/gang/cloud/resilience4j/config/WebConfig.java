package com.gang.cloud.resilience4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname WebConfig
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Configuration
public class WebConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
