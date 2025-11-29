package com.example.springaidep.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 聊天模型控制器
 * 展示不同 LLM 提供商的使用方式
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatModelController {

    private final ChatClient chatClient;

    /**
     * 基础聊天接口
     */
    @PostMapping("/simple")
    public Map<String, String> simpleChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        log.info("收到聊天请求: {}", message);
        
        String response = chatClient.call(message);
        
        return Map.of(
            "request", message,
            "response", response
        );
    }

    /**
     * 使用 Prompt 的聊天接口
     */
    @PostMapping("/prompt")
    public Map<String, Object> promptChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        
        Prompt prompt = new Prompt(new UserMessage(message));
        ChatResponse response = chatClient.call(prompt);
        
        return Map.of(
            "request", message,
            "response", response.getResult().getOutput().getContent(),
            "metadata", Map.of(
                "model", response.getMetadata().getModel(),
                "usage", response.getMetadata().getUsage()
            )
        );
    }

    /**
     * 带参数的聊天接口
     */
    @PostMapping("/with-options")
    public Map<String, Object> chatWithOptions(@RequestBody Map<String, Object> request) {
        String message = (String) request.get("message");
        Double temperature = request.containsKey("temperature") 
            ? ((Number) request.get("temperature")).doubleValue() 
            : 0.7;
        Integer maxTokens = request.containsKey("maxTokens") 
            ? ((Number) request.get("maxTokens")).intValue() 
            : 1000;
        
        // 注意: 具体的 Options 设置需要根据使用的模型提供商调整
        String response = chatClient.call(message);
        
        return Map.of(
            "request", message,
            "response", response,
            "options", Map.of(
                "temperature", temperature,
                "maxTokens", maxTokens
            )
        );
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "service", "ChatModel",
            "message", "Chat service is running"
        );
    }
}
