package com.example.springai.controller;

import com.example.springai.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/simple")
    public String simpleChat(@RequestBody String message) {
        return chatService.chat(message);
    }
    
    @PostMapping("/stream")
    public String streamChat(@RequestBody String message) {
        return chatService.streamChat(message);
    }
}
