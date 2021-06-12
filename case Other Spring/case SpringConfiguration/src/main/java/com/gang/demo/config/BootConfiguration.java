package com.gang.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname ClassConfiguration
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Configuration
@ConfigurationProperties(prefix = "default.boot")
@Component
public class BootConfiguration {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
