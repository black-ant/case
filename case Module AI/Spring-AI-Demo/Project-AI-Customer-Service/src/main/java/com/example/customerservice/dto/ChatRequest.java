package com.example.customerservice.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String sessionId;
    private String customerId;
    private String customerName;
    private String message;
    private boolean streaming = false;
}
