package com.example.multillm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class GeminiService {
    
    private final ChatClient chatClient;
    private final VertexAiGeminiChatModel chatModel;
    
    public GeminiService(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(chatModel).build();
    }
    
    public String chat(String message) {
        log.info("Google Gemini chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    public Flux<String> streamChat(String message) {
        log.info("Google Gemini stream chat request: {}", message);
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
