package com.example.springai.model;

import lombok.Data;

/**
 * 基础对话请求 DTO
 * 
 * 用于简单的对话场景，只需要提供用户消息即可。
 * 
 * 使用示例：
 * <pre>
 * ChatRequest request = new ChatRequest();
 * request.setMessage("你好，请介绍一下 Spring Boot");
 * </pre>
 * 
 * @author Spring AI Demo
 * @see com.example.springai.controller.ChatController
 */
@Data
public class ChatRequest {
    
    /**
     * 用户消息内容
     * 
     * 这是用户想要发送给 AI 的问题或指令。
     * 
     * 示例：
     * - "你好"
     * - "请解释一下什么是 Spring Boot"
     * - "帮我写一个 Java 单例模式的代码"
     */
    private String message;
}
