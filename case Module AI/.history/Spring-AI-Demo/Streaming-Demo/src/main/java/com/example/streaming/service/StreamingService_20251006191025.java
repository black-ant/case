package com.example.streaming.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 流式响应服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingService {
    
    private final ChatClient.Builder chatClientBuilder;
    
    /**
     * 1. 基础流式响应 - 返回文本内容
     */
    public Flux<String> basicStream(String message) {
        log.info("基础流式响应: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
    
    /**
     * 2. 完整流式响应 - 返回 ChatResponse 对象
     */
    public Flux<ChatResponse> fullStream(String message) {
        log.info("完整流式响应: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .chatResponse();
    }
    
    /**
     * 3. 带延迟的流式响应（模拟打字效果）
     */
    public Flux<String> delayedStream(String message, long delayMillis) {
        log.info("带延迟的流式响应: message={}, delay={}ms", message, delayMillis);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .delayElements(Duration.ofMillis(delayMillis));
    }
    
    /**
     * 4. 带系统提示词的流式响应
     */
    public Flux<String> streamWithSystem(String systemPrompt, String userMessage) {
        log.info("带系统提示词的流式响应: system={}, user={}", systemPrompt, userMessage);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .stream()
                .content();
    }
    
    /**
     * 5. 多轮对话流式响应
     */
    public Flux<String> conversationStream(String context, String message) {
        log.info("多轮对话流式响应: context={}, message={}", context, message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .system("对话上下文: " + context)
                .user(message)
                .stream()
                .content();
    }
    
    /**
     * 6. 带选项的流式响应
     */
    public Flux<String> streamWithOptions(String message, Double temperature, Integer maxTokens) {
        log.info("带选项的流式响应: message={}, temp={}, maxTokens={}", 
                message, temperature, maxTokens);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(temperature)
                        .withMaxTokens(maxTokens)
                        .build())
                .stream()
                .content();
    }
    
    /**
     * 7. 流式响应转换 - 添加前缀
     */
    public Flux<String> streamWithPrefix(String message, String prefix) {
        log.info("流式响应转换: message={}, prefix={}", message, prefix);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return Flux.concat(
                Flux.just(prefix),
                chatClient.prompt()
                        .user(message)
                        .stream()
                        .content()
        );
    }
    
    /**
     * 8. 流式响应过滤 - 只返回非空内容
     */
    public Flux<String> streamFiltered(String message) {
        log.info("流式响应过滤: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .filter(chunk -> chunk != null && !chunk.trim().isEmpty());
    }
    
    /**
     * 9. 流式响应计数
     */
    public Flux<String> streamWithCount(String message) {
        log.info("流式响应计数: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .index()
                .map(tuple -> {
                    long index = tuple.getT1();
                    String content = tuple.getT2();
                    if (index == 0) {
                        return "[开始] " + content;
                    }
                    return content;
                })
                .doOnComplete(() -> log.info("流式响应完成"));
    }
    
    /**
     * 10. 并行流式响应 - 同时请求多个问题
     */
    public Flux<String> parallelStream(String... messages) {
        log.info("并行流式响应: {} 个问题", messages.length);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return Flux.fromArray(messages)
                .flatMap(message -> 
                    chatClient.prompt()
                            .user(message)
                            .stream()
                            .content()
                            .map(chunk -> "[" + message.substring(0, Math.min(10, message.length())) + "...] " + chunk)
                );
    }
    
    /**
     * 11. 流式响应缓冲 - 按行返回
     */
    public Flux<String> streamBuffered(String message) {
        log.info("流式响应缓冲: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .scan("", (accumulated, chunk) -> accumulated + chunk)
                .filter(text -> text.contains("\n"))
                .map(text -> {
                    int lastNewline = text.lastIndexOf('\n');
                    return text.substring(0, lastNewline + 1);
                })
                .distinctUntilChanged();
    }
    
    /**
     * 12. 流式响应错误处理
     */
    public Flux<String> streamWithErrorHandling(String message) {
        log.info("流式响应错误处理: {}", message);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .onErrorResume(error -> {
                    log.error("流式响应错误: ", error);
                    return Flux.just("[错误] " + error.getMessage());
                })
                .doOnError(error -> log.error("流式响应失败", error));
    }
}
