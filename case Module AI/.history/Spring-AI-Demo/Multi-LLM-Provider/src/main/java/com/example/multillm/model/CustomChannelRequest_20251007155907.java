package com.example.multillm.model;

import lombok.Data;

@Data
public class CustomChannelRequest {
    private String message;
    private String baseUrl;
    private String apiToken;
    private String model;
}
