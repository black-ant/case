package com.example.agenttools.core;

import org.springframework.ai.chat.client.ChatClient;

import java.util.List;
import java.util.Objects;

/**
 * Minimal Agent demo to mirror the requested builder API.
 * It wires a ChatClient and a list of tool objects and exposes execute().
 * For demo purposes this Agent orchestrates simple tool usage heuristically.
 */
public class Agent {

    private final ChatClient chatClient;
    private final List<Object> tools;
    private final String systemPrompt;

    private Agent(ChatClient chatClient, List<Object> tools, String systemPrompt) {
        this.chatClient = chatClient;
        this.tools = tools;
        this.systemPrompt = systemPrompt;
    }

    public String execute(String userInput) {
        // Heuristic orchestration for demo: recognize weather query and optional DB save.
        String city = ChineseText.extractCityForWeather(userInput);
        WeatherTool weatherTool = ToolRegistry.findTool(tools, WeatherTool.class);
        DatabaseTool databaseTool = ToolRegistry.findTool(tools, DatabaseTool.class);

        StringBuilder sb = new StringBuilder();
        if (city != null && weatherTool != null) {
            String weather = weatherTool.queryWeather(city);
            sb.append("[Agent] 天气查询结果: ").append(weather);

            if (ChineseText.containsDbSaveIntent(userInput) && databaseTool != null) {
                databaseTool.saveWeather(city, weather);
                sb.append("；数据库已保存");
            }
            return sb.toString();
        }

        // Fallback to LLM for general dialogue if no tools matched (still works if no key configured).
        // The call is safe; downstream will fail only when actually calling a remote provider.
        try {
            return this.chatClient.prompt()
                    .system(this.systemPrompt)
                    .user(userInput)
                    .call()
                    .content();
        } catch (Exception e) {
            return "[Agent] 无法使用工具解析请求，且 LLM 调用不可用。输入: " + userInput;
        }
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private ChatClient chatClient;
        private List<Object> tools = List.of();
        private String systemPrompt = "你是智能助手，可调用工具完成任务";

        public Builder chatClient(ChatClient chatClient) {
            this.chatClient = chatClient; return this;
        }
        public Builder tools(List<Object> tools) {
            this.tools = tools != null ? tools : List.of(); return this;
        }
        public Builder systemPrompt(String systemPrompt) {
            this.systemPrompt = systemPrompt; return this;
        }
        public Agent build() {
            Objects.requireNonNull(chatClient, "chatClient must not be null");
            return new Agent(chatClient, tools, systemPrompt);
        }
    }

    // Simple internal helpers
    static class ToolRegistry {
        static <T> T findTool(List<Object> tools, Class<T> type) {
            for (Object t : tools) {
                if (type.isInstance(t)) return type.cast(t);
            }
            return null;
        }
    }

    static class ChineseText {
        static boolean containsDbSaveIntent(String text) {
            if (text == null) return false;
            return text.contains("保存") && (text.contains("数据库") || text.contains("存档"));
        }
        static String extractCityForWeather(String text) {
            if (text == null) return null;
            // Very naive Chinese intent extraction just for demo: find pattern "查询<city>天气" or "<city>天气"
            int q = text.indexOf("查询");
            int t = text.indexOf("天气");
            if (t >= 0) {
                int start = (q >= 0 && q + 2 < t) ? q + 2 : 0;
                String segment = text.substring(start, t).trim();
                if (!segment.isEmpty()) return segment.replaceAll("[^\\u4e00-\\u9fa5A-Za-z0-9]", "");
            }
            return null;
        }
    }
}

