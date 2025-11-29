package com.example.customerservice.service;

import com.example.customerservice.config.ApiKeyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 动态 ChatClient 服务 - 根据可用的 API Key 创建 ChatClient
 */
@Slf4j
@Service
public class DynamicChatClientService {
    
    @Autowired
    private ApiKeyManager apiKeyManager;
    
    // ChatClient 缓存
    private final Map<String, ChatClient> chatClientCache = new ConcurrentHashMap<>();
    
    /**
     * 获取可用的 ChatClient
     */
    public ChatClient getChatClient() {
        ApiKeyConfig.KeyConfig keyConfig = apiKeyManager.getAvailableChatKey();
        String cacheKey = keyConfig.getKey() + ":" + keyConfig.getBaseUrl() + ":" + keyConfig.getModel();
        
        return chatClientCache.computeIfAbsent(cacheKey, k -> createChatClient(keyConfig));
    }
    
    /**
     * 创建 ChatClient
     */
    private ChatClient createChatClient(ApiKeyConfig.KeyConfig keyConfig) {
        try {
            log.debug("Creating ChatClient with key: {}, baseUrl: {}, model: {}", 
                    maskApiKey(keyConfig.getKey()), keyConfig.getBaseUrl(), keyConfig.getModel());
            
            // 创建 OpenAI API 实例
            OpenAiApi openAiApi = new OpenAiApi(keyConfig.getBaseUrl(), keyConfig.getKey());
            
            // 创建 ChatModel
            OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi);
            
            // 创建 ChatClient
            return ChatClient.builder(chatModel).build();
                    
        } catch (Exception e) {
            log.error("Failed to create ChatClient with key: {}", maskApiKey(keyConfig.getKey()), e);
            apiKeyManager.recordKeyError(keyConfig.getKey(), e.getMessage());
            throw new RuntimeException("Failed to create ChatClient", e);
        }
    }
    
    /**
     * 执行聊天请求（带错误处理和重试）
     */
    public String chatWithRetry(String sessionId, String userMessage, 
                               java.util.function.Function<ChatClient, String> chatFunction) {
        int maxRetries = 3;
        Exception lastException = null;
        
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                ApiKeyConfig.KeyConfig currentKey = apiKeyManager.getAvailableChatKey();
                ChatClient chatClient = getChatClient();
                
                String result = chatFunction.apply(chatClient);
                
                // 记录成功
                apiKeyManager.recordKeySuccess(currentKey.getKey());
                
                return result;
                
            } catch (Exception e) {
                lastException = e;
                log.warn("Chat request failed (attempt {}/{}): {}", attempt, maxRetries, e.getMessage());
                
                // 记录错误
                try {
                    ApiKeyConfig.KeyConfig currentKey = apiKeyManager.getAvailableChatKey();
                    apiKeyManager.recordKeyError(currentKey.getKey(), e.getMessage());
                } catch (Exception keyError) {
                    log.error("Failed to record key error", keyError);
                }
                
                // 如果不是最后一次尝试，切换到下一个 Key
                if (attempt < maxRetries) {
                    apiKeyManager.switchToNextChatKey();
                    // 清除缓存，强制重新创建 ChatClient
                    chatClientCache.clear();
                    
                    try {
                        Thread.sleep(1000); // 等待1秒后重试
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        throw new RuntimeException("All chat attempts failed after " + maxRetries + " retries", lastException);
    }
    
    /**
     * 执行流式聊天请求（带错误处理和重试）
     */
    public reactor.core.publisher.Flux<String> chatStreamWithRetry(String sessionId, String userMessage,
                                                                  java.util.function.Function<ChatClient, reactor.core.publisher.Flux<String>> chatFunction) {
        return reactor.core.publisher.Flux.defer(() -> {
            try {
                ApiKeyConfig.KeyConfig currentKey = apiKeyManager.getAvailableChatKey();
                ChatClient chatClient = getChatClient();
                
                return chatFunction.apply(chatClient)
                        .doOnComplete(() -> apiKeyManager.recordKeySuccess(currentKey.getKey()))
                        .onErrorResume(error -> {
                            log.warn("Stream chat request failed: {}", error.getMessage());
                            apiKeyManager.recordKeyError(currentKey.getKey(), error.getMessage());
                            apiKeyManager.switchToNextChatKey();
                            chatClientCache.clear();
                            
                            // 重试一次
                            try {
                                ApiKeyConfig.KeyConfig retryKey = apiKeyManager.getAvailableChatKey();
                                ChatClient retryChatClient = getChatClient();
                                return chatFunction.apply(retryChatClient)
                                        .doOnComplete(() -> apiKeyManager.recordKeySuccess(retryKey.getKey()));
                            } catch (Exception retryError) {
                                return reactor.core.publisher.Flux.error(
                                        new RuntimeException("Stream chat retry failed", retryError));
                            }
                        });
                        
            } catch (Exception e) {
                return reactor.core.publisher.Flux.error(e);
            }
        });
    }
    
    /**
     * 清除 ChatClient 缓存
     */
    public void clearCache() {
        chatClientCache.clear();
        log.info("ChatClient cache cleared");
    }
    
    /**
     * 掩码 API Key
     */
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }
}