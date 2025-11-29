package com.example.agenttools.config;

import com.example.agenttools.core.Agent;
import com.example.agenttools.core.DatabaseTool;
import com.example.agenttools.core.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AgentBeansConfig {

    // Agent A: 能调用天气 + 数据库两个工具
    @Bean(name = "opsAgent")
    public Agent opsAgent(ChatClient.Builder builder, WeatherTool weatherTool, DatabaseTool databaseTool) {
        ChatClient client = builder
                .defaultSystem("你是智能助手，可调用工具完成任务")
                .build();
        return Agent.builder()
                .chatClient(client)
                .tools(List.of(weatherTool, databaseTool))
                .systemPrompt("你是智能助手，可调用工具完成任务")
                .build();
    }

    // Agent B: 只读天气查询，不做数据库保存
    @Bean(name = "readonlyWeatherAgent")
    public Agent readonlyWeatherAgent(ChatClient.Builder builder, WeatherTool weatherTool) {
        ChatClient client = builder
                .defaultSystem("你是天气顾问，只提供天气信息，不做任何持久化")
                .build();
        return Agent.builder()
                .chatClient(client)
                .tools(List.of(weatherTool))
                .systemPrompt("你是天气顾问，只提供天气信息，不做任何持久化")
                .build();
    }
}

