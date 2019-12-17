package com.gang.study.resttemplate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @Classname config
 * @Description TODO
 * @Date 2019/12/17 15:26
 * @Created by zengzg
 */
@Configuration
public class WebConfig {

    @Autowired
    private WebInterceptor webInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(webInterceptor));
        return restTemplate;
    }


}
