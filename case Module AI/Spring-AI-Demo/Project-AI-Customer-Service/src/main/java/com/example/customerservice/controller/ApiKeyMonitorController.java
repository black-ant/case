package com.example.customerservice.controller;

import com.example.customerservice.service.ApiKeyManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * API Key 监控控制器
 */
@RestController
@RequestMapping("/api/monitor")
public class ApiKeyMonitorController {
    
    @Autowired
    private ApiKeyManager apiKeyManager;
    
    /**
     * 获取所有 API Key 的使用统计
     */
    @GetMapping("/usage")
    public Map<String, Object> getUsageStats() {
        Map<String, Object> result = new HashMap<>();
        
        Map<String, ApiKeyManager.KeyUsageStats> usageStats = apiKeyManager.getAllKeyUsageStats();
        Map<String, UsageStatsDto> formattedStats = new HashMap<>();
        
        usageStats.forEach((key, stats) -> {
            UsageStatsDto dto = new UsageStatsDto();
            dto.setMaskedKey(maskApiKey(key));
            dto.setTotalUsage(stats.getTotalUsage());
            dto.setTodayUsage(stats.getTodayUsage());
            dto.setErrorCount(stats.getErrorCount());
            dto.setLastUsedTime(stats.getLastUsedTime());
            formattedStats.put(maskApiKey(key), dto);
        });
        
        result.put("usage", formattedStats);
        result.put("timestamp", LocalDateTime.now());
        
        return result;
    }
    
    /**
     * 获取所有 API Key 的健康状态
     */
    @GetMapping("/health")
    public Map<String, Object> getHealthStatus() {
        Map<String, Object> result = new HashMap<>();
        
        Map<String, ApiKeyManager.KeyHealthStatus> healthStatus = apiKeyManager.getAllKeyHealthStatus();
        Map<String, HealthStatusDto> formattedStatus = new HashMap<>();
        
        healthStatus.forEach((key, status) -> {
            HealthStatusDto dto = new HealthStatusDto();
            dto.setMaskedKey(maskApiKey(key));
            dto.setHealthy(status.isHealthy());
            dto.setLastErrorMessage(status.getLastErrorMessage());
            dto.setLastErrorTime(status.getLastErrorTime());
            dto.setLastSuccessTime(status.getLastSuccessTime());
            dto.setConsecutiveErrors(status.getConsecutiveErrors().get());
            dto.setConsecutiveSuccesses(status.getConsecutiveSuccesses().get());
            formattedStatus.put(maskApiKey(key), dto);
        });
        
        result.put("health", formattedStatus);
        result.put("timestamp", LocalDateTime.now());
        
        return result;
    }
    
    /**
     * 获取完整的监控信息
     */
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        
        // 使用统计
        Map<String, ApiKeyManager.KeyUsageStats> usageStats = apiKeyManager.getAllKeyUsageStats();
        Map<String, ApiKeyManager.KeyHealthStatus> healthStatus = apiKeyManager.getAllKeyHealthStatus();
        
        Map<String, KeyMonitorDto> keyMonitors = new HashMap<>();
        
        // 合并使用统计和健康状态
        usageStats.forEach((key, stats) -> {
            KeyMonitorDto dto = new KeyMonitorDto();
            dto.setMaskedKey(maskApiKey(key));
            dto.setTotalUsage(stats.getTotalUsage());
            dto.setTodayUsage(stats.getTodayUsage());
            dto.setErrorCount(stats.getErrorCount());
            dto.setLastUsedTime(stats.getLastUsedTime());
            
            ApiKeyManager.KeyHealthStatus health = healthStatus.get(key);
            if (health != null) {
                dto.setHealthy(health.isHealthy());
                dto.setLastErrorMessage(health.getLastErrorMessage());
                dto.setLastErrorTime(health.getLastErrorTime());
                dto.setLastSuccessTime(health.getLastSuccessTime());
                dto.setConsecutiveErrors(health.getConsecutiveErrors().get());
                dto.setConsecutiveSuccesses(health.getConsecutiveSuccesses().get());
            } else {
                dto.setHealthy(true);
                dto.setConsecutiveErrors(0);
                dto.setConsecutiveSuccesses(0);
            }
            
            keyMonitors.put(maskApiKey(key), dto);
        });
        
        result.put("keys", keyMonitors);
        result.put("summary", createSummary(keyMonitors));
        result.put("timestamp", LocalDateTime.now());
        
        return result;
    }
    
    /**
     * 手动切换 Chat API Key
     */
    @PostMapping("/switch/chat")
    public Map<String, Object> switchChatKey() {
        apiKeyManager.switchToNextChatKey();
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Switched to next Chat API Key");
        result.put("timestamp", LocalDateTime.now());
        
        return result;
    }
    
    /**
     * 手动切换 Embeddings API Key
     */
    @PostMapping("/switch/embeddings")
    public Map<String, Object> switchEmbeddingsKey() {
        apiKeyManager.switchToNextEmbeddingsKey();
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Switched to next Embeddings API Key");
        result.put("timestamp", LocalDateTime.now());
        
        return result;
    }
    
    /**
     * 创建摘要信息
     */
    private Map<String, Object> createSummary(Map<String, KeyMonitorDto> keyMonitors) {
        Map<String, Object> summary = new HashMap<>();
        
        long totalKeys = keyMonitors.size();
        long healthyKeys = keyMonitors.values().stream()
                .mapToLong(dto -> dto.isHealthy() ? 1 : 0)
                .sum();
        long totalUsageToday = keyMonitors.values().stream()
                .mapToLong(KeyMonitorDto::getTodayUsage)
                .sum();
        long totalErrors = keyMonitors.values().stream()
                .mapToLong(KeyMonitorDto::getErrorCount)
                .sum();
        
        summary.put("totalKeys", totalKeys);
        summary.put("healthyKeys", healthyKeys);
        summary.put("unhealthyKeys", totalKeys - healthyKeys);
        summary.put("totalUsageToday", totalUsageToday);
        summary.put("totalErrors", totalErrors);
        summary.put("healthRate", totalKeys > 0 ? (double) healthyKeys / totalKeys : 0.0);
        
        return summary;
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
    public static class UsageStatsDto {
        private String maskedKey;
        private long totalUsage;
        private long todayUsage;
        private long errorCount;
        private LocalDateTime lastUsedTime;
    }
    
    @Data
    public static class HealthStatusDto {
        private String maskedKey;
        private boolean healthy;
        private String lastErrorMessage;
        private LocalDateTime lastErrorTime;
        private LocalDateTime lastSuccessTime;
        private int consecutiveErrors;
        private int consecutiveSuccesses;
    }
    
    @Data
    public static class KeyMonitorDto {
        private String maskedKey;
        private long totalUsage;
        private long todayUsage;
        private long errorCount;
        private LocalDateTime lastUsedTime;
        private boolean healthy;
        private String lastErrorMessage;
        private LocalDateTime lastErrorTime;
        private LocalDateTime lastSuccessTime;
        private int consecutiveErrors;
        private int consecutiveSuccesses;
    }
}