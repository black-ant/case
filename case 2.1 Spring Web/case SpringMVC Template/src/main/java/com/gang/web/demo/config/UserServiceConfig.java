package com.gang.web.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.user-service")
public class UserServiceConfig {
    // 支持不同命名风格
    private int maxConnections;
    private long connectionTimeout;
}

