package com.example.springai.controller;

import com.example.common.audit.ApiAuditLog;
import com.example.springai.model.ChatRequest;
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
    @ApiAuditLog("简单对话")
    @PostMapping("/simple")
    public String simpleChat(@RequestBody ChatRequest request) {
        return chatService.chat(request.getMessage());
    }
    
    /**
     * 流式对话 - 使用 Server-Sent Events (SSE) 实时推送响应片段
     * 客户端会实时接收到 AI 生成的每个文本片段
     */
    @ApiAuditLog("流式对话")
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody ChatRequest request) {
        return chatService.streamChat(request.getMessage());
    }
}
