package com.example.customerservice.controller;

import com.example.customerservice.dto.ChatRequest;
import com.example.customerservice.dto.ChatResponse;
import com.example.customerservice.service.CustomerServiceAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * AI 客服控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/customer-service")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerServiceController {
    
    private final CustomerServiceAgent customerServiceAgent;
    
    /**
     * 创建会话
     */
    @PostMapping("/session")
    public ChatResponse createSession(@RequestBody ChatRequest request) {
        String sessionId = customerServiceAgent.createSession(
            request.getCustomerId(),
            request.getCustomerName()
        );
        return new ChatResponse(sessionId, "会话创建成功，欢迎使用AI客服！");
    }
    
    /**
     * 发送消息（非流式）
     */
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        log.info("收到消息: sessionId={}, message={}", request.getSessionId(), request.getMessage());
        
        String response = customerServiceAgent.chat(
            request.getSessionId(),
            request.getCustomerId(),
            request.getMessage()
        );
        
        return new ChatResponse(request.getSessionId(), response);
    }
    
    /**
     * 发送消息（流式）
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        log.info("收到流式消息: sessionId={}, message={}", request.getSessionId(), request.getMessage());
        
        return customerServiceAgent.chatStream(
            request.getSessionId(),
            request.getCustomerId(),
            request.getMessage()
        );
    }
    
    /**
     * 结束会话
     */
    @PostMapping("/session/{sessionId}/end")
    public ChatResponse endSession(@PathVariable String sessionId) {
        customerServiceAgent.endSession(sessionId);
        return new ChatResponse(sessionId, "会话已结束，感谢使用！");
    }
}
