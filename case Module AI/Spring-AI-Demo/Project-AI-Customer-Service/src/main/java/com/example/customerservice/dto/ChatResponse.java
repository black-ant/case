package com.example.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String sessionId;
    private String message;
    private long timestamp;
    
    public ChatResponse(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
