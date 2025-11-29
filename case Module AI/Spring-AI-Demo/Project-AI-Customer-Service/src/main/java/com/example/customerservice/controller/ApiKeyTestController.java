package com.example.customerservice.controller;

import com.example.customerservice.service.ApiKeyManager;
import com.example.customerservice.service.DynamicChatClientService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * API Key 测试控制器 - 用于测试多 Key 轮询和负载均衡
 */
@RestController
@RequestMapping("/api/test")
public class ApiKeyTestController {
    
    @Autowired
    private ApiKeyManager apiKeyManager;
    
    @Autowired
    private DynamicChatClientService dynamicChatClientService;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    /**
     * 测试单次 API 调用
     */
    @PostMapping("/single")
    public Map<String, Object> testSingleCall(@RequestBody TestRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 模拟聊天请求
            String response = dynamicChatClientService.chatWithRetry("test-session", request.getMessage(), chatClient -> {
                // 这里只是模拟，实际应该调用 chatClient
                return "测试响应: " + request.getMessage();
            });
            
            long endTime = System.currentTimeMillis();
            
            result.put("success", true);
            result.put("response", response);
            result.put("duration", endTime - startTime);
            result.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        
        return result;
    }
    
    /**
     * 测试并发 API 调用（压力测试）
     */
    @PostMapping("/concurrent")
    public Map<String, Object> testConcurrentCalls(@RequestBody ConcurrentTestRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 创建并发任务
            CompletableFuture<?>[] futures = new CompletableFuture[request.getConcurrency()];
            
            for (int i = 0; i < request.getConcurrency(); i++) {
                final int taskId = i;
                futures[i] = CompletableFuture.runAsync(() -> {
                    try {
                        for (int j = 0; j < request.getRequestsPerTask(); j++) {
                            String message = String.format("Task-%d Request-%d: %s", taskId, j, request.getMessage());
                            
                            dynamicChatClientService.chatWithRetry("test-session-" + taskId, message, chatClient -> {
                                // 模拟处理时间
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                                return "Response for " + message;
                            });
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Task " + taskId + " failed", e);
                    }
                }, executorService);
            }
            
            // 等待所有任务完成
            CompletableFuture.allOf(futures).join();
            
            long endTime = System.currentTimeMillis();
            
            result.put("success", true);
            result.put("totalRequests", request.getConcurrency() * request.getRequestsPerTask());
            result.put("concurrency", request.getConcurrency());
            result.put("duration", endTime - startTime);
            result.put("requestsPerSecond", (double) (request.getConcurrency() * request.getRequestsPerTask()) / ((endTime - startTime) / 1000.0));
            result.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        
        return result;
    }
    
    /**
     * 模拟 API Key 错误
     */
    @PostMapping("/simulate-error")
    public Map<String, Object> simulateError(@RequestBody SimulateErrorRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 模拟记录错误
            apiKeyManager.recordKeyError(request.getApiKey(), request.getErrorMessage());
            
            result.put("success", true);
            result.put("message", "Error simulated for API Key: " + maskApiKey(request.getApiKey()));
            result.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        
        return result;
    }
    
    /**
     * 重置 API Key 统计
     */
    @PostMapping("/reset-stats")
    public Map<String, Object> resetStats() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 清除缓存
            dynamicChatClientService.clearCache();
            
            result.put("success", true);
            result.put("message", "Statistics and cache cleared");
            result.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        
        return result;
    }
    
    /**
     * 获取当前 API Key 状态
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("usageStats", apiKeyManager.getAllKeyUsageStats());
            result.put("healthStatus", apiKeyManager.getAllKeyHealthStatus());
            result.put("timestamp", LocalDateTime.now());
            
        } catch (Exception e) {
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        
        return result;
    }
    
    /**
     * 掩码 API Key
     */
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }
    
    @Data
    public static class TestRequest {
        private String message = "Hello, this is a test message";
    }
    
    @Data
    public static class ConcurrentTestRequest {
        private String message = "Concurrent test message";
        private int concurrency = 5;
        private int requestsPerTask = 3;
    }
    
    @Data
    public static class SimulateErrorRequest {
        private String apiKey;
        private String errorMessage = "Simulated API error";
    }
}