package com.example.customerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * API 配置集合 - 管理多个配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiConfigurationCollection {
    
    /**
     * 当前活动的配置 ID
     */
    private String activeConfigId;
    
    /**
     * 所有配置列表
     */
    private List<ApiConfiguration> configurations;
    
    /**
     * 创建默认集合
     */
    public static ApiConfigurationCollection createDefault() {
        ApiConfigurationCollection collection = new ApiConfigurationCollection();
        collection.setConfigurations(new ArrayList<>());
        
        // 创建默认配置
        ApiConfiguration defaultConfig = ApiConfiguration.createDefault("默认配置");
        collection.getConfigurations().add(defaultConfig);
        collection.setActiveConfigId(defaultConfig.getId());
        
        return collection;
    }
    
    /**
     * 获取活动配置
     */
    public ApiConfiguration getActiveConfiguration() {
        if (activeConfigId == null || configurations == null) {
            return null;
        }
        
        return configurations.stream()
            .filter(config -> config.getId().equals(activeConfigId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * 根据 ID 获取配置
     */
    public ApiConfiguration getConfigurationById(String id) {
        if (id == null || configurations == null) {
            return null;
        }
        
        return configurations.stream()
            .filter(config -> config.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * 添加配置
     */
    public void addConfiguration(ApiConfiguration config) {
        if (configurations == null) {
            configurations = new ArrayList<>();
        }
        configurations.add(config);
    }
    
    /**
     * 删除配置
     */
    public boolean removeConfiguration(String id) {
        if (configurations == null) {
            return false;
        }
        
        return configurations.removeIf(config -> config.getId().equals(id));
    }
    
    /**
     * 更新配置
     */
    public boolean updateConfiguration(ApiConfiguration updatedConfig) {
        if (configurations == null) {
            return false;
        }
        
        for (int i = 0; i < configurations.size(); i++) {
            if (configurations.get(i).getId().equals(updatedConfig.getId())) {
                configurations.set(i, updatedConfig);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 设置活动配置
     */
    public boolean setActiveConfiguration(String id) {
        ApiConfiguration config = getConfigurationById(id);
        if (config == null) {
            return false;
        }
        
        // 取消所有配置的活动状态
        configurations.forEach(c -> c.setActive(false));
        
        // 设置新的活动配置
        config.setActive(true);
        this.activeConfigId = id;
        
        return true;
    }
}
