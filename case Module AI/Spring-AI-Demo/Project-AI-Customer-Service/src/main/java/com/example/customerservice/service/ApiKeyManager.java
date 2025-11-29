package com.example.customerservice.service;

import com.example.customerservice.config.ApiKeyConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * API Key 管理器 - 实现多 Key 轮询、用量监控和自动切换
 */
@Slf4j
@Service
public class ApiKeyManager {

    @Autowired
    private ApiKeyConfig apiKeyConfig;

    // Key 使用统计
    private final Map<String, KeyUsageStats> keyUsageStats = new ConcurrentHashMap<>();

    // 当前使用的 Key 索引
    private final AtomicInteger currentChatKeyIndex = new AtomicInteger(0);
    private final AtomicInteger currentEmbeddingsKeyIndex = new AtomicInteger(0);

    // Key 健康状态
    private final Map<String, KeyHealthStatus> keyHealthStatus = new ConcurrentHashMap<>();

    /**
     * 获取可用的 Chat API Key
     */
    public ApiKeyConfig.KeyConfig getAvailableChatKey() {
        return getAvailableKey(apiKeyConfig.getChat(), currentChatKeyIndex, "Chat");
    }

    /**
     * 获取可用的 Embeddings API Key
     */
    public ApiKeyConfig.KeyConfig getAvailableEmbeddingsKey() {
        return getAvailableKey(apiKeyConfig.getEmbeddings(), currentEmbeddingsKeyIndex, "Embeddings");
    }

    /**
     * 获取可用的 Key（通用方法）
     */
    private ApiKeyConfig.KeyConfig getAvailableKey(List<ApiKeyConfig.KeyConfig> keys,
            AtomicInteger currentIndex,
            String keyType) {
        if (keys.isEmpty()) {
            throw new RuntimeException("No " + keyType + " API keys configured");
        }

        // 按优先级排序
        keys.sort(Comparator.comparingInt(ApiKeyConfig.KeyConfig::getPriority));

        // 尝试找到可用的 Key
        for (int i = 0; i < keys.size(); i++) {
            int index = (currentIndex.get() + i) % keys.size();
            ApiKeyConfig.KeyConfig keyConfig = keys.get(index);

            if (isKeyAvailable(keyConfig)) {
                currentIndex.set(index);
                recordKeyUsage(keyConfig.getKey(), keyType);
                log.debug("Using {} API Key: {} (index: {})", keyType, maskApiKey(keyConfig.getKey()), index);
                return keyConfig;
            }
        }

        // 如果所有 Key 都不可用，使用第一个（可能会失败，但至少有错误信息）
        log.warn("All {} API keys are unavailable, using first key", keyType);
        ApiKeyConfig.KeyConfig fallbackKey = keys.get(0);
        recordKeyUsage(fallbackKey.getKey(), keyType);
        return fallbackKey;
    }

    /**
     * 检查 Key 是否可用
     */
    private boolean isKeyAvailable(ApiKeyConfig.KeyConfig keyConfig) {
        if (!keyConfig.isEnabled()) {
            return false;
        }

        String keyId = keyConfig.getKey();

        // 检查健康状态
        KeyHealthStatus healthStatus = keyHealthStatus.get(keyId);
        if (healthStatus != null && !healthStatus.isHealthy()) {
            return false;
        }

        // 检查每日配额
        if (keyConfig.getDailyLimit() > 0) {
            KeyUsageStats stats = keyUsageStats.get(keyId);
            if (stats != null && stats.getTodayUsage() >= keyConfig.getDailyLimit()) {
                log.warn("API Key {} has reached daily limit: {}", maskApiKey(keyId), keyConfig.getDailyLimit());
                return false;
            }
        }

        return true;
    }

    /**
     * 记录 Key 使用情况
     */
    public void recordKeyUsage(String apiKey, String keyType) {
        KeyUsageStats stats = keyUsageStats.computeIfAbsent(apiKey, k -> new KeyUsageStats());
        stats.incrementUsage();

        log.debug("API Key {} usage: today={}, total={}",
                maskApiKey(apiKey), stats.getTodayUsage(), stats.getTotalUsage());
    }

    /**
     * 记录 Key 错误
     */
    public void recordKeyError(String apiKey, String errorMessage) {
        KeyUsageStats stats = keyUsageStats.computeIfAbsent(apiKey, k -> new KeyUsageStats());
        stats.incrementError();

        // 更新健康状态
        KeyHealthStatus healthStatus = keyHealthStatus.computeIfAbsent(apiKey, k -> new KeyHealthStatus());
        healthStatus.recordError(errorMessage);

        log.warn("API Key {} error recorded: {} (total errors: {})",
                maskApiKey(apiKey), errorMessage, stats.getErrorCount());

        // 如果错误过多，标记为不健康
        if (stats.getErrorCount() >= 5) {
            healthStatus.setHealthy(false);
            log.error("API Key {} marked as unhealthy due to too many errors", maskApiKey(apiKey));
        }
    }

    /**
     * 记录 Key 成功使用
     */
    public void recordKeySuccess(String apiKey) {
        KeyHealthStatus healthStatus = keyHealthStatus.computeIfAbsent(apiKey, k -> new KeyHealthStatus());
        healthStatus.recordSuccess();

        // 如果之前不健康，现在恢复健康
        if (!healthStatus.isHealthy()) {
            healthStatus.setHealthy(true);
            log.info("API Key {} recovered and marked as healthy", maskApiKey(apiKey));
        }
    }

    /**
     * 强制切换到下一个 Key
     */
    public void switchToNextChatKey() {
        currentChatKeyIndex.incrementAndGet();
        log.info("Switched to next Chat API Key");
    }

    public void switchToNextEmbeddingsKey() {
        currentEmbeddingsKeyIndex.incrementAndGet();
        log.info("Switched to next Embeddings API Key");
    }

    /**
     * 获取所有 Key 的使用统计
     */
    public Map<String, KeyUsageStats> getAllKeyUsageStats() {
        return new HashMap<>(keyUsageStats);
    }

    /**
     * 获取所有 Key 的健康状态
     */
    public Map<String, KeyHealthStatus> getAllKeyHealthStatus() {
        return new HashMap<>(keyHealthStatus);
    }

    /**
     * 重置每日使用统计（每天凌晨执行）
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyUsage() {
        log.info("Resetting daily usage statistics for all API keys");
        keyUsageStats.values().forEach(KeyUsageStats::resetDailyUsage);
    }

    /**
     * 定期健康检查（每小时执行）
     */
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void performHealthCheck() {
        log.info("Performing health check for all API keys");

        // 重置长时间未使用的不健康状态
        keyHealthStatus.entrySet().removeIf(entry -> {
            KeyHealthStatus status = entry.getValue();
            if (!status.isHealthy() &&
                    status.getLastErrorTime().isBefore(LocalDateTime.now().minusHours(2))) {
                log.info("Resetting health status for API Key {}", maskApiKey(entry.getKey()));
                return true;
            }
            return false;
        });
    }

    /**
     * 掩码 API Key（用于日志）
     */
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }

    /**
     * Key 使用统计
     */
    @Data
    public static class KeyUsageStats {
        private final AtomicLong totalUsage = new AtomicLong(0);
        private final AtomicLong todayUsage = new AtomicLong(0);
        private final AtomicLong errorCount = new AtomicLong(0);
        private LocalDate lastResetDate = LocalDate.now();
        private LocalDateTime lastUsedTime = LocalDateTime.now();

        public void incrementUsage() {
            checkAndResetDaily();
            totalUsage.incrementAndGet();
            todayUsage.incrementAndGet();
            lastUsedTime = LocalDateTime.now();
        }

        public void incrementError() {
            errorCount.incrementAndGet();
        }

        public void resetDailyUsage() {
            todayUsage.set(0);
            lastResetDate = LocalDate.now();
        }

        private void checkAndResetDaily() {
            if (!lastResetDate.equals(LocalDate.now())) {
                resetDailyUsage();
            }
        }

        public long getTotalUsage() {
            return totalUsage.get();
        }

        public long getTodayUsage() {
            checkAndResetDaily();
            return todayUsage.get();
        }

        public long getErrorCount() {
            return errorCount.get();
        }
    }

    /**
     * Key 健康状态
     */
    @Data
    public static class KeyHealthStatus {
        private boolean healthy = true;
        private String lastErrorMessage;
        private LocalDateTime lastErrorTime;
        private LocalDateTime lastSuccessTime = LocalDateTime.now();
        private final AtomicInteger consecutiveErrors = new AtomicInteger(0);
        private final AtomicInteger consecutiveSuccesses = new AtomicInteger(0);

        public void recordError(String errorMessage) {
            this.lastErrorMessage = errorMessage;
            this.lastErrorTime = LocalDateTime.now();
            consecutiveErrors.incrementAndGet();
            consecutiveSuccesses.set(0);

            // 连续错误超过3次标记为不健康
            if (consecutiveErrors.get() >= 3) {
                this.healthy = false;
            }
        }

        public void recordSuccess() {
            this.lastSuccessTime = LocalDateTime.now();
            consecutiveSuccesses.incrementAndGet();
            consecutiveErrors.set(0);

            // 连续成功超过2次恢复健康
            if (consecutiveSuccesses.get() >= 2) {
                this.healthy = true;
            }
        }
    }
}