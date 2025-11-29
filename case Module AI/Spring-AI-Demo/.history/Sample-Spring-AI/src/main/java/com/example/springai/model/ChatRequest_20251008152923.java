package com.example.springai.model;

import lombok.Data;

/**
 * 基础对话请求 DTO
 * 
 * @author Spring AI Demo
 */
@Data
public class ChatRequest {
    
    /** 用户消息内容 */
    private String message;
}
