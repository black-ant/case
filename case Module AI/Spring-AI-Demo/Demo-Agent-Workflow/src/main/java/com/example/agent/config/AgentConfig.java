package com.example.agent.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 智能体配置
 */
@Configuration
public class AgentConfig {
    
    /**
     * 创建专业化的 ChatClient 实例
     */
    @Bean
    public Map<String, ChatClient> specializedClients(ChatClient.Builder builder) {
        return Map.of(
                "technical", builder
                        .defaultSystem("你是一个资深软件工程师和技术专家，擅长编程、架构设计和技术问题解答。")
                        .build(),
                
                "business", builder
                        .defaultSystem("你是一个商业顾问和金融分析师，擅长商业战略、市场分析和财务规划。")
                        .build(),
                
                "creative", builder
                        .defaultSystem("你是一个创意总监和内容策略专家，擅长创意写作、设计和艺术表达。")
                        .build(),
                
                "scientific", builder
                        .defaultSystem("你是一个科学研究专家，擅长科学分析、数学推理和学术研究。")
                        .build(),
                
                "general", builder
                        .defaultSystem("你是一个友好专业的通用助手，能够回答各种问题。")
                        .build()
        );
    }
    
    /**
     * 配置异步任务执行器（用于并行工作流）
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("agent-workflow-");
        executor.initialize();
        return executor;
    }
}
