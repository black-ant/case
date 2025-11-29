package com.example.multillm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class CustomChannelService {
    
    /**
     * 使用自定义 BaseURL 和 API Token 创建聊天
     */
    public String chat(String message, String baseUrl, String apiToken, String model) {
        String actualModel = model != null && !model.isEmpty() ? model : "gpt-3.5-turbo";
        log.info("Starting custom channel chat");
        log.info("BaseURL: {}", baseUrl);
        log.info("Model: {}", actualModel);
        log.info("Message length: {} characters", message.length());
        log.debug("Message content: {}", message.substring(0, Math.min(100, message.length())));
        
        try {
            // 创建自定义的 OpenAI API 客户端
            log.debug("Creating OpenAI API client with custom baseUrl");
            OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiToken);
            
            // 创建聊天模型
            log.debug("Creating chat model with options - Model: {}, Temperature: 0.7", actualModel);
            OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, 
                OpenAiChatOptions.builder()
                    .withModel(actualModel)
                    .withTemperature(0.7)
                    .build());
            
            // 创建 ChatClient 并执行对话
            log.debug("Building ChatClient and executing prompt");
            ChatClient chatClient = ChatClient.builder(chatModel).build();
            
            long startTime = System.currentTimeMillis();
            String response = chatClient.prompt()
                    .user(message)
                    .call()
                    .content();
            long duration = System.currentTimeMillis() - startTime;
            
            log.info("Custom channel chat completed successfully");
            log.info("Response time: {}ms", duration);
            log.info("Response length: {} characters", response.length());
            log.debug("Response preview: {}", response.substring(0, Math.min(200, response.length())));
            
            return response;
        } catch (Exception e) {
            log.error("Custom channel chat failed", e);
            log.error("Error details - BaseURL: {}, Model: {}", baseUrl, actualModel);
            log.error("Error message: {}", e.getMessage());
            throw new RuntimeException("Custom channel chat failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * 使用自定义 BaseURL 和 API Token 创建流式聊天
     */
    public Flux<String> streamChat(String message, String baseUrl, String apiToken, String model) {
        String actualModel = model != null && !model.isEmpty() ? model : "gpt-3.5-turbo";
        log.info("Starting custom channel stream chat");
        log.info("BaseURL: {}", baseUrl);
        log.info("Model: {}", actualModel);
        log.info("Message length: {} characters", message.length());
        log.debug("Message content: {}", message.substring(0, Math.min(100, message.length())));
        
        try {
            // 创建自定义的 OpenAI API 客户端
            log.debug("Creating OpenAI API client with custom baseUrl for streaming");
            OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiToken);
            
            // 创建聊天模型
            log.debug("Creating chat model for streaming - Model: {}, Temperature: 0.7", actualModel);
            OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, 
                OpenAiChatOptions.builder()
                    .withModel(actualModel)
                    .withTemperature(0.7)
                    .build());
            
            // 创建 ChatClient 并执行流式对话
            log.debug("Building ChatClient and starting stream");
            ChatClient chatClient = ChatClient.builder(chatModel).build();
            
            final long startTime = System.currentTimeMillis();
            final StringBuilder fullResponse = new StringBuilder();
            
            return chatClient.prompt()
                    .user(message)
                    .stream()
                    .content()
                    .doOnNext(chunk -> {
                        fullResponse.append(chunk);
                        log.trace("Received chunk: {}", chunk);
                    })
                    .doOnComplete(() -> {
                        long duration = System.currentTimeMillis() - startTime;
                        log.info("Custom channel stream completed successfully");
                        log.info("Stream duration: {}ms", duration);
                        log.info("Total response length: {} characters", fullResponse.length());
                        log.debug("Full response: {}", fullResponse.toString().substring(0, Math.min(200, fullResponse.length())));
                    })
                    .doOnError(e -> {
                        log.error("Custom channel stream failed", e);
                        log.error("Error details - BaseURL: {}, Model: {}", baseUrl, actualModel);
                        log.error("Error message: {}", e.getMessage());
                    });
        } catch (Exception e) {
            log.error("Failed to initialize custom channel stream", e);
            log.error("Error details - BaseURL: {}, Model: {}", baseUrl, actualModel);
            return Flux.error(new RuntimeException("Custom channel stream initialization failed: " + e.getMessage(), e));
        }
    }
}
