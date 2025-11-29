package com.example.multillm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class OpenAIService {
    
    private final ChatClient chatClient;
    private final OpenAiChatModel chatModel;
    
    public OpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(chatModel).build();
    }
    
    public String chat(String message) {
        log.info("OpenAI chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    public Flux<String> streamChat(String message) {
        log.info("OpenAI stream chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
