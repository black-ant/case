package com.example.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * 展示 ChatClient 的高级配置和用法
 */
@Service
public class AdvancedChatService {
    
    private final ChatClient chatClient;
    
    public AdvancedChatService(ChatClient.Builder chatClientBuilder) {
        // 使用 Builder 创建带默认配置的 ChatClient
        this.chatClient = chatClientBuilder
                .defaultSystem("你是一个专业的 AI 助手，擅长技术问题解答。")
                .defaultOptions(OpenAiChatOptions.builder()
                        .withModel("gpt-3.5-turbo")
                        .withTemperature(0.7)
                        .withMaxTokens(1000)
                        .build())
                .build();
    }
    
    /**
     * 1. 基础配置：设置 temperature 和 max tokens
     */
    public String chatWithBasicOptions(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.3)  // 更确定性的输出
                        .withMaxTokens(500)    // 限制输出长度
                        .build())
                .call()
                .content();
    }
    
    /**
     * 2. 系统提示词配置
     */
    public String chatWithSystemPrompt(String message, String systemPrompt) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }
    
    /**
     * 3. 使用模板和变量
     */
    public String chatWithTemplate(String userName, String topic) {
        return chatClient.prompt()
                .system("你是一个友好的助手，正在与 " + userName + " 对话。")
                .user("请详细介绍一下 " + topic)
                .call()
                .content();
    }
    
    /**
     * 4. 多轮对话（带历史记录）
     */
    public String chatWithHistory(List<String> history, String newMessage) {
        // 将历史记录拼接到系统提示中
        StringBuilder context = new StringBuilder("你是一个有记忆的助手。以下是对话历史：\n");
        for (int i = 0; i < history.size(); i++) {
            context.append("用户: ").append(history.get(i)).append("\n");
        }
        
        return chatClient.prompt()
                .system(context.toString())
                .user(newMessage)
                .call()
                .content();
    }
    
    /**
     * 5. 高级选项配置：top_p, frequency_penalty, presence_penalty
     */
    public String chatWithAdvancedOptions(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel("gpt-3.5-turbo")
                        .withTemperature(0.8)
                        .withTopP(0.9)                    // 核采样
                        .withFrequencyPenalty(0.5)        // 降低重复词频率
                        .withPresencePenalty(0.5)         // 鼓励谈论新话题
                        .withMaxTokens(2000)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 6. 流式响应配置
     */
    public Flux<String> streamWithOptions(String message) {
        return chatClient.prompt()
                .system("你是一个专业的技术写作助手。")
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.7)
                        .withMaxTokens(1500)
                        .build())
                .stream()
                .content();
    }
    
    /**
     * 7. 使用 Function Calling（工具调用）
     */
    public String chatWithFunctionCalling(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel("gpt-3.5-turbo")
                        .withTemperature(0.7)
                        // Function calling 配置会在后续版本中添加
                        .build())
                .call()
                .content();
    }
    
    /**
     * 8. 使用 Prompt 对象进行更底层的控制
     */
    public String chatWithPromptObject(String systemText, String userText) {
        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage(systemText),
                        new UserMessage(userText)
                ),
                OpenAiChatOptions.builder()
                        .withModel("gpt-3.5-turbo")
                        .withTemperature(0.7)
                        .withMaxTokens(1000)
                        .withTopP(0.95)
                        .withFrequencyPenalty(0.0)
                        .withPresencePenalty(0.0)
                        .build()
        );
        
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
    
    /**
     * 9. 获取完整的响应信息（包括 token 使用情况）
     */
    public Map<String, Object> chatWithFullResponse(String message) {
        var response = chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
        
        return Map.of(
                "content", response.getResult().getOutput().getContent(),
                "model", response.getMetadata().getModel(),
                "finishReason", response.getResult().getMetadata().getFinishReason(),
                "promptTokens", response.getMetadata().getUsage().getPromptTokens(),
                "completionTokens", response.getMetadata().getUsage().getGenerationTokens(),
                "totalTokens", response.getMetadata().getUsage().getTotalTokens()
        );
    }
    
    /**
     * 10. 使用不同的模型
     */
    public String chatWithSpecificModel(String message, String model) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel(model)  // 可以是 gpt-3.5-turbo, gpt-4, gpt-4-turbo 等
                        .withTemperature(0.7)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 11. 创造性写作配置（高 temperature）
     */
    public String creativeWriting(String topic) {
        return chatClient.prompt()
                .system("你是一个富有创造力的作家，擅长写作引人入胜的故事。")
                .user("请写一个关于 " + topic + " 的短故事")
                .options(OpenAiChatOptions.builder()
                        .withTemperature(1.0)      // 最大创造性
                        .withTopP(1.0)
                        .withPresencePenalty(0.6)  // 鼓励新话题
                        .withFrequencyPenalty(0.6) // 避免重复
                        .withMaxTokens(2000)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 12. 精确回答配置（低 temperature）
     */
    public String preciseAnswer(String question) {
        return chatClient.prompt()
                .system("你是一个严谨的技术专家，只提供准确的事实性答案。")
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.1)      // 最小随机性
                        .withTopP(0.1)
                        .withMaxTokens(500)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 13. 使用 Advisor（拦截器）模式
     */
    public String chatWithAdvisor(String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(spec -> {
                    // 可以在这里添加请求/响应拦截器
                    // 例如：日志记录、内容过滤、提示词增强等
                })
                .call()
                .content();
    }
}
