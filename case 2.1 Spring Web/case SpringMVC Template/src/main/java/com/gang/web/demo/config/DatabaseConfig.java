package com.gang.web.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.database")
public class DatabaseConfig {
    // 支持复杂嵌套
    private Database primary;
    private Database secondary;

    @Data
    public static class Database {
        private String host;
        private int port;
        private String username;
        private String password;
    }
}

