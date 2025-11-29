package com.example.springai.controller;

import com.example.springai.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    /**
     * 简单对话 - 等待完整响应后一次性返回
     */
    @PostMapping(value = "/simple", consumes = "text/plain")
    public String simpleChat(@RequestBody String message) {
        return chatService.chat(message);
    }
    
    /**
     * 流式对话 - 使用 Server-Sent Events (SSE) 实时推送响应片段
     * 客户端会实时接收到 AI 生成的每个文本片段
     */
    @PostMapping(value = "/stream", consumes = "text/plain", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody String message) {
        return chatService.streamChat(message);
    }
}
