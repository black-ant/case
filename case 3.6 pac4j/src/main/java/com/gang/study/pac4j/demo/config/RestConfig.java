package com.gang.study.pac4j.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname RestConfig
 * @Description TODO
 * @Date 2020/5/26 23:19
 * @Created by zengzg
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate gerRestTemplate() {
        return new RestTemplate();
    }
}
