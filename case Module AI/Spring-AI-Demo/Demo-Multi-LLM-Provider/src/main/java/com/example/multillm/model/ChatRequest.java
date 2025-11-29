package com.example.multillm.model;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private Double temperature;
    private String model;
}
