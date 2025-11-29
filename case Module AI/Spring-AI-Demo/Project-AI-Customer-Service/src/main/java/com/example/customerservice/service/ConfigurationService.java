package com.example.customerservice.service;

import com.example.customerservice.model.ApiConfiguration;
import com.example.customerservice.model.ApiConfigurationCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 配置管理服务 - 支持多配置管理
 */
@Slf4j
@Service
public class ConfigurationService {
    
    private static final String CONFIG_FILE = "data/api-configs.json";
    private final ObjectMapper objectMapper;
    private ApiConfigurationCollection configCollection;
    
    public ConfigurationService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * 应用启动时加载配置集合
     */
    @PostConstruct
    public void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try {
                log.info("从文件加载 API 配置集合: {}", CONFIG_FILE);
                configCollection = objectMapper.readValue(configFile, ApiConfigurationCollection.class);
                log.info("API 配置集合加载成功，共 {} 个配置", configCollection.getConfigurations().size());
                logConfigSummary();
            } catch (IOException e) {
                log.error("加载 API 配置失败，使用默认配置", e);
                configCollection = ApiConfigurationCollection.createDefault();
                saveConfiguration();
            }
        } else {
            log.info("配置文件不存在，创建默认配置: {}", CONFIG_FILE);
            configCollection = ApiConfigurationCollection.createDefault();
            
            // 确保目录存在
            try {
                Path dataDir = Paths.get("data");
                if (!Files.exists(dataDir)) {
                    Files.createDirectories(dataDir);
                    log.info("创建数据目录: {}", dataDir);
                }
            } catch (IOException e) {
                log.error("创建数据目录失败", e);
            }
            
            saveConfiguration();
        }
    }
    
    /**
     * 应用关闭时保存配置
     */
    @PreDestroy
    public void saveOnShutdown() {
        log.info("应用关闭，保存 API 配置");
        saveConfiguration();
    }
    
    /**
     * 保存配置到文件
     */
    public void saveConfiguration() {
        try {
            File configFile = new File(CONFIG_FILE);
            objectMapper.writeValue(configFile, configCollection);
            log.info("API 配置集合已保存到: {}", CONFIG_FILE);
        } catch (IOException e) {
            log.error("保存 API 配置失败", e);
            throw new RuntimeException("保存配置失败", e);
        }
    }
    
    /**
     * 获取所有配置
     */
    public List<ApiConfiguration> getAllConfigurations() {
        return configCollection.getConfigurations();
    }
    
    /**
     * 获取当前活动配置
     */
    public ApiConfiguration getActiveConfiguration() {
        return configCollection.getActiveConfiguration();
    }
    
    /**
     * 根据 ID 获取配置
     */
    public ApiConfiguration getConfigurationById(String id) {
        return configCollection.getConfigurationById(id);
    }
    
    /**
     * 创建新配置
     */
    public ApiConfiguration createConfiguration(String name, String description) {
        log.info("创建新配置: {}", name);
        
        ApiConfiguration newConfig = ApiConfiguration.createDefault(name);
        newConfig.setDescription(description);
        newConfig.setActive(false);
        
        configCollection.addConfiguration(newConfig);
        saveConfiguration();
        
        return newConfig;
    }
    
    /**
     * 更新配置
     */
    public boolean updateConfiguration(ApiConfiguration updatedConfig) {
        log.info("更新配置: {}", updatedConfig.getName());
        
        updatedConfig.setUpdatedAt(LocalDateTime.now());
        boolean success = configCollection.updateConfiguration(updatedConfig);
        
        if (success) {
            saveConfiguration();
            logConfigSummary();
        }
        
        return success;
    }
    
    /**
     * 删除配置
     */
    public boolean deleteConfiguration(String id) {
        log.info("删除配置: {}", id);
        
        // 不能删除活动配置
        if (id.equals(configCollection.getActiveConfigId())) {
            log.warn("不能删除活动配置");
            return false;
        }
        
        boolean success = configCollection.removeConfiguration(id);
        
        if (success) {
            saveConfiguration();
        }
        
        return success;
    }
    
    /**
     * 设置活动配置
     */
    public boolean setActiveConfiguration(String id) {
        log.info("设置活动配置: {}", id);
        
        boolean success = configCollection.setActiveConfiguration(id);
        
        if (success) {
            saveConfiguration();
            logConfigSummary();
        }
        
        return success;
    }
    
    /**
     * 复制配置
     */
    public ApiConfiguration duplicateConfiguration(String id, String newName) {
        log.info("复制配置: {} -> {}", id, newName);
        
        ApiConfiguration original = getConfigurationById(id);
        if (original == null) {
            return null;
        }
        
        ApiConfiguration duplicate = new ApiConfiguration();
        duplicate.setId(UUID.randomUUID().toString());
        duplicate.setName(newName);
        duplicate.setDescription(original.getDescription() + " (副本)");
        duplicate.setActive(false);
        duplicate.setCreatedAt(LocalDateTime.now());
        duplicate.setUpdatedAt(LocalDateTime.now());
        duplicate.setChatApi(original.getChatApi());
        duplicate.setEmbeddingsApi(original.getEmbeddingsApi());
        duplicate.setSystemPrompt(original.getSystemPrompt());
        duplicate.setOtherConfig(original.getOtherConfig());
        
        configCollection.addConfiguration(duplicate);
        saveConfiguration();
        
        return duplicate;
    }
    

    
    /**
     * 打印配置摘要
     */
    private void logConfigSummary() {
        ApiConfiguration activeConfig = getActiveConfiguration();
        if (activeConfig == null) {
            log.warn("没有活动配置");
            return;
        }
        
        log.info("=== 活动 API 配置摘要 ===");
        log.info("配置名称: {}", activeConfig.getName());
        log.info("配置 ID: {}", activeConfig.getId());
        log.info("Chat API Base URL: {}", activeConfig.getChatApi().getBaseUrl());
        log.info("Chat API Model: {}", activeConfig.getChatApi().getModel());
        log.info("Chat API Keys: {} 个", activeConfig.getChatApi().getApiKeys().size());
        log.info("Embeddings API Base URL: {}", activeConfig.getEmbeddingsApi().getBaseUrl());
        log.info("Embeddings API Model: {}", activeConfig.getEmbeddingsApi().getModel());
        log.info("Embeddings API Keys: {} 个", activeConfig.getEmbeddingsApi().getApiKeys().size());
        log.info("Function Calling: {}", activeConfig.getOtherConfig().getEnableFunctionCalling());
        log.info("Streaming: {}", activeConfig.getOtherConfig().getEnableStreaming());
        log.info("========================");
    }
    
    /**
     * 验证配置是否有效
     */
    public boolean validateConfiguration(String id) {
        ApiConfiguration config = getConfigurationById(id);
        if (config == null) {
            return false;
        }
        
        // 检查必要的配置项
        if (config.getChatApi() == null || 
            config.getChatApi().getBaseUrl() == null ||
            config.getChatApi().getApiKeys().isEmpty()) {
            return false;
        }
        
        if (config.getEmbeddingsApi() == null ||
            config.getEmbeddingsApi().getBaseUrl() == null ||
            config.getEmbeddingsApi().getApiKeys().isEmpty()) {
            return false;
        }
        
        return true;
    }
}
