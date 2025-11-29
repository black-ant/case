package com.gang.web.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.time.Duration;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    // 自动类型转换
    private Duration timeout;     // 5秒
    private DataSize size;        // 10兆字节
    private Integer retryCount;   // 3
    private Boolean isEnabled;    // true
}
