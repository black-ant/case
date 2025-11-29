package com.example.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    private final ChatClient chatClient;
    
    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    /**
     * 简单对话示例
     */
    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    /**
     * 流式对话示例
     */
    public String streamChat(String message) {
        StringBuilder response = new StringBuilder();
        chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .forEach(response::append);
        return response.toString();
    }
}
