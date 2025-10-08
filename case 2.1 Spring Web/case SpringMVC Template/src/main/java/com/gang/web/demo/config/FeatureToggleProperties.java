package com.gang.web.demo.config;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.feature")
@Validated  // 启用属性校验
@Data
public class FeatureToggleProperties {
    // 用户注册配置
    private UserRegistration userRegistration;

    // 管理员面板配置
    private AdminPanel adminPanel;

    // 高级特性
    private Advanced advanced;

    // 第三方集成
    private Integrations integrations;

    // 限流配置
    private RateLimits rateLimits;

    // 实验性功能
    private Experiments experiments;

    // 内部静态类
    @Data
    public static class UserRegistration {
        @NotNull
        private Boolean enabled;

        private Boolean requireEmailVerification;

        private Integer minPasswordLength;
    }

    @Data
    public static class AdminPanel {
        private Boolean enabled;
        private List<String> allowedIps;
        private Integer maxLoginAttempts;
    }

    @Data
    public static class Advanced {
        private Boolean debugMode;
        private Boolean performanceTracking;
    }

    @Data
    public static class Integrations {
        private Github github;

        private Slack slack;

        @Data
        public static class Github {
            private Boolean enabled;
            private String webhookSecret;
        }

        @Data
        public static class Slack {
            private Boolean enabled;
            private String channel;
        }
    }

    @Data
    public static class RateLimits {
        private ApiRateLimit api;

        private LoginRateLimit login;

        @Data
        public static class ApiRateLimit {
            private Boolean enabled;
            private Integer requestsPerMinute;
        }

        @Data
        public static class LoginRateLimit {
            private Boolean enabled;
            private Integer maxAttempts;
            private Duration lockoutDuration;
        }
    }

    @Data
    public static class Experiments {
        private NewDashboard newDashboard;

        private AiSuggestions aiSuggestions;

        @Data
        public static class NewDashboard {
            private Boolean enabled;

            private Integer rolloutPercentage;
        }

        @Data
        public static class AiSuggestions {
            private Boolean enabled;
            private String model;
        }
    }
}