package com.example.customerservice.controller;

import com.example.customerservice.model.ApiConfiguration;
import com.example.customerservice.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API 配置管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConfigurationController {
    
    private final ConfigurationService configurationService;
    
    /**
     * 获取所有配置
     */
    @GetMapping
    public Map<String, Object> getAllConfigurations() {
        log.info("获取所有 API 配置");
        
        List<ApiConfiguration> configs = configurationService.getAllConfigurations();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("configurations", configs.stream()
            .map(this::maskSensitiveData)
            .toList());
        
        return response;
    }
    
    /**
     * 获取活动配置
     */
    @GetMapping("/active")
    public Map<String, Object> getActiveConfiguration() {
        log.info("获取活动 API 配置");
        
        ApiConfiguration config = configurationService.getActiveConfiguration();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("config", maskSensitiveData(config));
        
        return response;
    }
    
    /**
     * 根据 ID 获取配置
     */
    @GetMapping("/{id}")
    public Map<String, Object> getConfigurationById(@PathVariable String id) {
        log.info("获取配置: {}", id);
        
        ApiConfiguration config = configurationService.getConfigurationById(id);
        
        Map<String, Object> response = new HashMap<>();
        if (config != null) {
            response.put("success", true);
            response.put("config", maskSensitiveData(config));
        } else {
            response.put("success", false);
            response.put("message", "配置不存在");
        }
        
        return response;
    }
    
    /**
     * 创建新配置
     */
    @PostMapping
    public Map<String, Object> createConfiguration(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String description = request.getOrDefault("description", "");
        
        log.info("创建新配置: {}", name);
        
        try {
            ApiConfiguration newConfig = configurationService.createConfiguration(name, description);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "配置创建成功");
            response.put("config", maskSensitiveData(newConfig));
            
            return response;
        } catch (Exception e) {
            log.error("创建配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "创建失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 更新配置
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateConfiguration(
            @PathVariable String id,
            @RequestBody ApiConfiguration updatedConfig) {
        log.info("更新配置: {}", id);
        
        try {
            updatedConfig.setId(id);
            boolean success = configurationService.updateConfiguration(updatedConfig);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "配置更新成功" : "配置不存在");
            
            return response;
        } catch (Exception e) {
            log.error("更新配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 删除配置
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteConfiguration(@PathVariable String id) {
        log.info("删除配置: {}", id);
        
        try {
            boolean success = configurationService.deleteConfiguration(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "配置删除成功" : "无法删除配置（可能是活动配置）");
            
            return response;
        } catch (Exception e) {
            log.error("删除配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "删除失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 设置活动配置
     */
    @PostMapping("/{id}/activate")
    public Map<String, Object> activateConfiguration(@PathVariable String id) {
        log.info("激活配置: {}", id);
        
        try {
            boolean success = configurationService.setActiveConfiguration(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "配置已激活" : "配置不存在");
            
            return response;
        } catch (Exception e) {
            log.error("激活配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "激活失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 复制配置
     */
    @PostMapping("/{id}/duplicate")
    public Map<String, Object> duplicateConfiguration(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        String newName = request.get("name");
        
        log.info("复制配置: {} -> {}", id, newName);
        
        try {
            ApiConfiguration duplicate = configurationService.duplicateConfiguration(id, newName);
            
            Map<String, Object> response = new HashMap<>();
            if (duplicate != null) {
                response.put("success", true);
                response.put("message", "配置复制成功");
                response.put("config", maskSensitiveData(duplicate));
            } else {
                response.put("success", false);
                response.put("message", "原配置不存在");
            }
            
            return response;
        } catch (Exception e) {
            log.error("复制配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "复制失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 验证配置
     */
    @GetMapping("/{id}/validate")
    public Map<String, Object> validateConfiguration(@PathVariable String id) {
        log.info("验证配置: {}", id);
        
        boolean isValid = configurationService.validateConfiguration(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("valid", isValid);
        response.put("message", isValid ? "配置有效" : "配置无效，请检查必填项");
        
        return response;
    }
    
    /**
     * 手动保存配置
     */
    @PostMapping("/save")
    public Map<String, Object> saveConfiguration() {
        log.info("手动保存配置");
        
        try {
            configurationService.saveConfiguration();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "配置已保存到 data/api-config.json");
            
            return response;
        } catch (Exception e) {
            log.error("保存配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "保存失败: " + e.getMessage());
            
            return response;
        }
    }
    
    /**
     * 脱敏处理敏感数据
     */
    private ApiConfiguration maskSensitiveData(ApiConfiguration config) {
        ApiConfiguration masked = new ApiConfiguration();
        
        // Chat API 配置
        ApiConfiguration.ChatApiConfig chatApi = new ApiConfiguration.ChatApiConfig();
        chatApi.setBaseUrl(config.getChatApi().getBaseUrl());
        chatApi.setModel(config.getChatApi().getModel());
        chatApi.setTemperature(config.getChatApi().getTemperature());
        chatApi.setMaxTokens(config.getChatApi().getMaxTokens());
        
        // 脱敏 API Keys
        List<String> maskedChatKeys = config.getChatApi().getApiKeys().stream()
            .map(this::maskApiKey)
            .toList();
        chatApi.setApiKeys(maskedChatKeys);
        masked.setChatApi(chatApi);
        
        // Embeddings API 配置
        ApiConfiguration.EmbeddingsApiConfig embeddingsApi = new ApiConfiguration.EmbeddingsApiConfig();
        embeddingsApi.setBaseUrl(config.getEmbeddingsApi().getBaseUrl());
        embeddingsApi.setModel(config.getEmbeddingsApi().getModel());
        
        // 脱敏 API Keys
        List<String> maskedEmbeddingsKeys = config.getEmbeddingsApi().getApiKeys().stream()
            .map(this::maskApiKey)
            .toList();
        embeddingsApi.setApiKeys(maskedEmbeddingsKeys);
        masked.setEmbeddingsApi(embeddingsApi);
        
        // 系统提示词和其他配置不需要脱敏
        masked.setSystemPrompt(config.getSystemPrompt());
        masked.setOtherConfig(config.getOtherConfig());
        
        return masked;
    }
    
    /**
     * 脱敏 API Key
     */
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 10) {
            return "***";
        }
        
        String prefix = apiKey.substring(0, 7);
        String suffix = apiKey.substring(apiKey.length() - 4);
        return prefix + "***" + suffix;
    }
}
