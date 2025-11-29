package com.example.functioncalling.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Function Calling 服务
 */
@Slf4j
@Service
public class FunctionCallingService {
    
    private final ChatClient chatClient;
    
    public FunctionCallingService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    /**
     * 1. 单个函数调用
     */
    public String callSingleFunction(String message, String functionName) {
        log.info("单个函数调用: message={}, function={}", message, functionName);
        
        return chatClient.prompt()
                .user(message)
                .functions(functionName)  // 指定可用的函数
                .call()
                .content();
    }
    
    /**
     * 2. 多个函数调用（AI 自动选择）
     */
    public String callMultipleFunctions(String message) {
        log.info("多函数调用: message={}", message);
        
        return chatClient.prompt()
                .user(message)
                .functions("weatherFunction", "stockPriceFunction", "calculatorFunction")
                .call()
                .content();
    }
    
    /**
     * 3. 自动函数调用（注册所有可用函数）
     */
    public String callWithAutoFunctions(String message) {
        log.info("自动函数调用: message={}", message);
        
        // AI 会根据问题自动选择合适的函数
        return chatClient.prompt()
                .user(message)
                .functions("weatherFunction", "stockPriceFunction", "calculatorFunction")
                .options(OpenAiChatOptions.builder()
                        .withFunctionCallbacks(Set.of())  // 可以动态添加函数
                        .build())
                .call()
                .content();
    }
    
    /**
     * 4. 强制函数调用
     */
    public String forceFunction(String message, String functionName) {
        log.info("强制函数调用: message={}, function={}", message, functionName);
        
        return chatClient.prompt()
                .user(message)
                .functions(functionName)
                .options(OpenAiChatOptions.builder()
                        // 强制 AI 必须调用指定的函数
                        .build())
                .call()
                .content();
    }
    
    /**
     * 5. 链式函数调用（多步骤）
     */
    public String chainFunctionCalls(String message) {
        log.info("链式函数调用: message={}", message);
        
        // AI 可能会连续调用多个函数来完成任务
        return chatClient.prompt()
                .system("你是一个智能助手，可以调用多个工具来完成复杂任务。")
                .user(message)
                .functions("weatherFunction", "stockPriceFunction", "calculatorFunction")
                .call()
                .content();
    }
    
    /**
     * 6. 带上下文的函数调用
     */
    public String callWithContext(String message, String context) {
        log.info("带上下文的函数调用: message={}, context={}", message, context);
        
        return chatClient.prompt()
                .system("上下文信息：" + context)
                .user(message)
                .functions("weatherFunction", "stockPriceFunction", "calculatorFunction")
                .call()
                .content();
    }
}
