package com.example.agent.workflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 路由工作流
 * 根据输入内容智能路由到不同的专业处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoutingWorkflow {
    
    private final ChatClient.Builder chatClientBuilder;
    private final Map<String, ChatClient> specializedClients;
    
    /**
     * 智能路由查询
     */
    public Map<String, Object> routeQuery(String query) {
        log.info("开始路由查询: {}", query);
        
        // 步骤1：分类查询类型
        ChatClient classifier = chatClientBuilder.build();
        
        String category = classifier.prompt()
                .system("""
                    将查询分类到以下类别之一：
                    - technical: 编程、软件、IT 相关问题
                    - business: 金融、营销、战略相关问题
                    - creative: 写作、设计、艺术相关问题
                    - scientific: 科学、数学、研究相关问题
                    - general: 其他所有问题
                    
                    只返回类别名称，不要其他内容。
                    """)
                .user(query)
                .call()
                .content()
                .trim()
                .toLowerCase();
        
        log.info("查询分类为: {}", category);
        
        // 步骤2：路由到专门的处理器
        ChatClient specializedClient = specializedClients.getOrDefault(
                category, 
                specializedClients.get("general")
        );
        
        String response = specializedClient.prompt()
                .user(query)
                .call()
                .content();
        
        return Map.of(
                "query", query,
                "category", category,
                "response", response
        );
    }
    
    /**
     * 复杂度路由
     * 根据问题复杂度选择不同的处理策略
     */
    public Map<String, Object> routeByComplexity(String query) {
        log.info("开始复杂度路由: {}", query);
        
        ChatClient classifier = chatClientBuilder.build();
        
        // 评估复杂度
        String complexity = classifier.prompt()
                .system("""
                    评估问题的复杂度：
                    - simple: 简单问题，可以直接回答
                    - moderate: 中等复杂度，需要一些分析
                    - complex: 复杂问题，需要深入分析和多步骤处理
                    
                    只返回复杂度级别。
                    """)
                .user(query)
                .call()
                .content()
                .trim()
                .toLowerCase();
        
        log.info("问题复杂度: {}", complexity);
        
        String response;
        String strategy;
        
        switch (complexity) {
            case "simple":
                strategy = "直接回答";
                response = handleSimpleQuery(query);
                break;
            case "moderate":
                strategy = "分析后回答";
                response = handleModerateQuery(query);
                break;
            case "complex":
                strategy = "多步骤深入分析";
                response = handleComplexQuery(query);
                break;
            default:
                strategy = "默认处理";
                response = handleSimpleQuery(query);
        }
        
        return Map.of(
                "query", query,
                "complexity", complexity,
                "strategy", strategy,
                "response", response
        );
    }
    
    /**
     * 处理简单查询
     */
    private String handleSimpleQuery(String query) {
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }
    
    /**
     * 处理中等复杂度查询
     */
    private String handleModerateQuery(String query) {
        ChatClient chatClient = chatClientBuilder.build();
        
        // 先分析，再回答
        String analysis = chatClient.prompt()
                .system("分析这个问题的关键点和需要考虑的因素。")
                .user(query)
                .call()
                .content();
        
        return chatClient.prompt()
                .system("基于以下分析，给出详细的回答：\n" + analysis)
                .user(query)
                .call()
                .content();
    }
    
    /**
     * 处理复杂查询
     */
    private String handleComplexQuery(String query) {
        ChatClient chatClient = chatClientBuilder.build();
        
        // 步骤1：分解问题
        String decomposition = chatClient.prompt()
                .system("将这个复杂问题分解成几个子问题。")
                .user(query)
                .call()
                .content();
        
        // 步骤2：分析每个子问题
        String subAnalysis = chatClient.prompt()
                .system("分析每个子问题。")
                .user(decomposition)
                .call()
                .content();
        
        // 步骤3：综合回答
        return chatClient.prompt()
                .system("基于子问题的分析，给出全面的回答。")
                .user("原问题：" + query + "\n\n分析：" + subAnalysis)
                .call()
                .content();
    }
    
    /**
     * 情感路由
     * 根据用户情感选择不同的回应风格
     */
    public Map<String, Object> routeByEmotion(String message) {
        log.info("开始情感路由: {}", message);
        
        ChatClient classifier = chatClientBuilder.build();
        
        String emotion = classifier.prompt()
                .system("""
                    识别用户消息的情感：
                    - happy: 开心、兴奋
                    - sad: 悲伤、沮丧
                    - angry: 生气、愤怒
                    - confused: 困惑、不解
                    - neutral: 中性
                    
                    只返回情感类别。
                    """)
                .user(message)
                .call()
                .content()
                .trim()
                .toLowerCase();
        
        log.info("识别情感: {}", emotion);
        
        // 根据情感选择回应风格
        String systemPrompt = switch (emotion) {
            case "happy" -> "用户很开心，以积极热情的方式回应。";
            case "sad" -> "用户情绪低落，以温暖关怀的方式回应。";
            case "angry" -> "用户有些生气，以冷静理解的方式回应。";
            case "confused" -> "用户感到困惑，以清晰耐心的方式解释。";
            default -> "以友好专业的方式回应。";
        };
        
        String response = classifier.prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
        
        return Map.of(
                "message", message,
                "emotion", emotion,
                "response", response
        );
    }
}
