package com.example.customerservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * API Key 配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "api-keys")
public class ApiKeyConfig {
    
    /**
     * Chat API Keys 配置列表
     */
    private List<KeyConfig> chat = new ArrayList<>();
    
    /**
     * Embeddings API Keys 配置列表
     */
    private List<KeyConfig> embeddings = new ArrayList<>();
    
    @Data
    public static class KeyConfig {
        /**
         * API Key
         */
        private String key;
        
        /**
         * API Base URL
         */
        private String baseUrl;
        
        /**
         * 模型名称
         */
        private String model;
        
        /**
         * 每日配额限制（可选，-1 表示无限制）
         */
        private int dailyLimit = -1;
        
        /**
         * 优先级（数字越小优先级越高）
         */
        private int priority = 0;
        
        /**
         * 是否启用
         */
        private boolean enabled = true;
    }
}
