package com.example.multillm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AnthropicService {
    
    private final ChatClient chatClient;
    private final AnthropicChatModel chatModel;
    
    public AnthropicService(AnthropicChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(chatModel).build();
    }
    
    public String chat(String message) {
        log.info("Anthropic Claude chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    public Flux<String> streamChat(String message) {
        log.info("Anthropic Claude stream chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
