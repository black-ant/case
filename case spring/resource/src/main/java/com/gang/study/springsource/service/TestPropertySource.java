package com.gang.study.springsource.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "com.gang.study", ignoreUnknownFields = false)
@PropertySource("classpath:message.properties")
@Data
@Component
public class TestPropertySource {

    private String test;

    private String test1;

    private String test2;
}
