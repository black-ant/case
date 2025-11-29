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
 * ChatService - 提供各种 ChatClient 配置示例
 * 
 * 包含从基础到高级的各种配置方式，帮助快速上手 Spring AI
 */
@Service
public class ChatService {
    
    private final ChatClient chatClient;
    private final ChatClient.Builder chatClientBuilder;
    
    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
        // 创建默认的 ChatClient
        this.chatClient = chatClientBuilder.build();
    }
    
    // ==================== 基础用法 ====================
    
    /**
     * 1. 简单对话 - 最基础的用法
     */
    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    /**
     * 2. 流式对话 - 实时返回 AI 生成的内容片段
     */
    public Flux<String> streamChat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
    
    // ==================== 系统提示词配置 ====================
    
    /**
     * 3. 带系统提示词的对话 - 定义 AI 的角色和行为
     */
    public String chatWithSystem(String systemPrompt, String userMessage) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .call()
                .content();
    }
    
    /**
     * 4. 使用预定义角色 - 技术助手
     */
    public String chatAsTechExpert(String question) {
        return chatClient.prompt()
                .system("你是一个资深的技术专家，擅长解答编程、架构和技术问题。" +
                        "请用清晰、专业的语言回答，必要时提供代码示例。")
                .user(question)
                .call()
                .content();
    }
    
    /**
     * 5. 使用预定义角色 - 翻译助手
     */
    public String translate(String text, String targetLanguage) {
        return chatClient.prompt()
                .system("你是一个专业的翻译助手，能够准确地将文本翻译成目标语言。" +
                        "保持原文的语气和风格，确保翻译自然流畅。")
                .user("请将以下文本翻译成" + targetLanguage + "：\n" + text)
                .call()
                .content();
    }
    
    // ==================== 参数配置 ====================
    
    /**
     * 6. 配置 Temperature - 控制输出的随机性
     * Temperature 越高（0-2），输出越有创造性；越低，输出越确定
     */
    public String chatWithTemperature(String message, double temperature) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(temperature)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 7. 配置 MaxTokens - 限制输出长度
     */
    public String chatWithMaxTokens(String message, int maxTokens) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withMaxTokens(maxTokens)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 8. 完整参数配置 - 精确控制输出
     */
    public String chatWithFullOptions(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.7)           // 平衡创造性和确定性
                        .withMaxTokens(1000)            // 最大输出 token 数
                        .withTopP(0.9)                  // 核采样，控制词汇多样性
                        .withFrequencyPenalty(0.5)      // 降低重复词频率
                        .withPresencePenalty(0.5)       // 鼓励谈论新话题
                        .build())
                .call()
                .content();
    }
    
    // ==================== 模型选择 ====================
    
    /**
     * 9. 指定模型 - 使用特定的 AI 模型
     */
    public String chatWithModel(String message, String model) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel(model)  // 例如: gpt-3.5-turbo, gpt-4, deepseek-ai/DeepSeek-V3
                        .build())
                .call()
                .content();
    }
    
    /**
     * 10. 快速模型 - 适合简单任务
     */
    public String chatFast(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel("gpt-3.5-turbo")
                        .withTemperature(0.7)
                        .withMaxTokens(500)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 11. 高级模型 - 适合复杂任务
     */
    public String chatAdvanced(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withModel("gpt-4")
                        .withTemperature(0.7)
                        .withMaxTokens(2000)
                        .build())
                .call()
                .content();
    }
    
    // ==================== 特殊场景配置 ====================
    
    /**
     * 12. 创造性写作 - 高 temperature，鼓励创新
     */
    public String creativeWriting(String topic) {
        return chatClient.prompt()
                .system("你是一个富有创造力的作家，擅长写作引人入胜的内容。")
                .user("请写一篇关于 " + topic + " 的文章")
                .options(OpenAiChatOptions.builder()
                        .withTemperature(1.0)           // 最大创造性
                        .withTopP(1.0)
                        .withPresencePenalty(0.6)       // 鼓励新话题
                        .withFrequencyPenalty(0.6)      // 避免重复
                        .withMaxTokens(2000)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 13. 精确回答 - 低 temperature，追求准确性
     */
    public String preciseAnswer(String question) {
        return chatClient.prompt()
                .system("你是一个严谨的专家，只提供准确的事实性答案。")
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.1)           // 最小随机性
                        .withTopP(0.1)
                        .withMaxTokens(500)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 14. 代码生成 - 适合编程任务
     */
    public String generateCode(String requirement) {
        return chatClient.prompt()
                .system("你是一个专业的程序员，擅长编写高质量、可维护的代码。" +
                        "请提供完整的代码实现，包含必要的注释。")
                .user(requirement)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.3)           // 较低的随机性，保证代码质量
                        .withMaxTokens(2000)
                        .build())
                .call()
                .content();
    }
    
    /**
     * 15. 摘要生成 - 提取关键信息
     */
    public String summarize(String text) {
        return chatClient.prompt()
                .system("你是一个专业的文本摘要助手，能够提取关键信息并生成简洁的摘要。")
                .user("请为以下文本生成摘要：\n" + text)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.3)
                        .withMaxTokens(300)
                        .build())
                .call()
                .content();
    }
    
    // ==================== 高级用法 ====================
    
    /**
     * 16. 使用 Prompt 对象 - 更底层的控制
     */
    public String chatWithPromptObject(String systemText, String userText) {
        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage(systemText),
                        new UserMessage(userText)
                ),
                OpenAiChatOptions.builder()
                        .withTemperature(0.7)
                        .withMaxTokens(1000)
                        .build()
        );
        
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
    
    /**
     * 17. 获取完整响应信息 - 包括 token 使用情况
     */
    public Map<String, Object> chatWithMetadata(String message) {
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
     * 18. 流式响应带配置
     */
    public Flux<String> streamWithOptions(String message, String systemPrompt) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .withTemperature(0.7)
                        .withMaxTokens(1500)
                        .build())
                .stream()
                .content();
    }
    
    /**
     * 19. 多轮对话 - 带上下文
     */
    public String chatWithContext(String context, String newMessage) {
        return chatClient.prompt()
                .system("你是一个有记忆的助手。以下是对话上下文：\n" + context)
                .user(newMessage)
                .call()
                .content();
    }
    
    /**
     * 20. 自定义 ChatClient - 带默认配置
     */
    public String chatWithCustomClient(String message) {
        ChatClient customClient = chatClientBuilder
                .defaultSystem("你是一个友好的助手")
                .defaultOptions(OpenAiChatOptions.builder()
                        .withTemperature(0.8)
                        .withMaxTokens(1000)
                        .build())
                .build();
        
        return customClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
