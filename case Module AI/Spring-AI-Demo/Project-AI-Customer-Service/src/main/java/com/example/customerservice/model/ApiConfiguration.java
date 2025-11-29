package com.example.customerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * API 配置模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfiguration {
    
    /**
     * 配置 ID
     */
    private String id;
    
    /**
     * 配置名称
     */
    private String name;
    
    /**
     * 配置描述
     */
    private String description;
    
    /**
     * 是否为活动配置
     */
    private Boolean active;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * Chat API 配置
     */
    private ChatApiConfig chatApi;
    
    /**
     * Embeddings API 配置
     */
    private EmbeddingsApiConfig embeddingsApi;
    
    /**
     * 系统提示词
     */
    private String systemPrompt;
    
    /**
     * 其他配置
     */
    private OtherConfig otherConfig;
    
    /**
     * Chat API 配置
     */
    @Data
    @AllArgsConstructor
    public static class ChatApiConfig {
        private String baseUrl;
        private List<String> apiKeys;
        private String model;
        private Double temperature;
        private Integer maxTokens;
        
        public ChatApiConfig() {
            this.apiKeys = new ArrayList<>();
            this.temperature = 0.7;
            this.maxTokens = 2000;
        }
    }
    
    /**
     * Embeddings API 配置
     */
    @Data
    @AllArgsConstructor
    public static class EmbeddingsApiConfig {
        private String baseUrl;
        private List<String> apiKeys;
        private String model;
        
        public EmbeddingsApiConfig() {
            this.apiKeys = new ArrayList<>();
        }
    }
    
    /**
     * 其他配置
     */
    @Data
    @AllArgsConstructor
    public static class OtherConfig {
        private Boolean enableFunctionCalling;
        private Boolean enableStreaming;
        private Integer sessionTimeout;
        private Integer maxHistoryMessages;
        
        public OtherConfig() {
            this.enableFunctionCalling = true;
            this.enableStreaming = true;
            this.sessionTimeout = 3600;
            this.maxHistoryMessages = 10;
        }
    }
    
    /**
     * 创建默认配置
     */
    public static ApiConfiguration createDefault(String name) {
        ApiConfiguration config = new ApiConfiguration();
        
        // 基本信息
        config.setId(UUID.randomUUID().toString());
        config.setName(name);
        config.setDescription("默认配置");
        config.setActive(true);
        config.setCreatedAt(LocalDateTime.now());
        config.setUpdatedAt(LocalDateTime.now());
        
        // Chat API 默认配置
        ChatApiConfig chatApi = new ChatApiConfig();
        chatApi.setBaseUrl("https://api.siliconflow.cn/v1");
        chatApi.setModel("Qwen/Qwen2.5-7B-Instruct");
        config.setChatApi(chatApi);
        
        // Embeddings API 默认配置
        EmbeddingsApiConfig embeddingsApi = new EmbeddingsApiConfig();
        embeddingsApi.setBaseUrl("https://api.siliconflow.cn/v1");
        embeddingsApi.setModel("BAAI/bge-large-zh-v1.5");
        config.setEmbeddingsApi(embeddingsApi);
        
        // 默认系统提示词
        config.setSystemPrompt(
            "你是一个专业的AI客服助手，负责帮助用户解决问题。\n" +
            "你应该：\n" +
            "1. 友好、专业地回答用户问题\n" +
            "2. 使用知识库中的信息提供准确答案\n" +
            "3. 在需要时调用相关功能查询订单、物流等信息\n" +
            "4. 如果不确定答案，诚实告知用户并提供其他帮助方式"
        );
        
        // 其他配置
        config.setOtherConfig(new OtherConfig());
        
        return config;
    }
}
