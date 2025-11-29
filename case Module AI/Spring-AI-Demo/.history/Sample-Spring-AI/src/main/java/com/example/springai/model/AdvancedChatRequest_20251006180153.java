package com.example.springai.model;

import lombok.Data;

import java.util.List;

@Data
public class AdvancedChatRequest {
    private String message;
    private String systemPrompt;
    private String userName;
    private String topic;
    private String model;
    private Double temperature;
    private Integer maxTokens;
    private Double topP;
    private Double frequencyPenalty;
    private Double presencePenalty;
    private List<String> history;
}
