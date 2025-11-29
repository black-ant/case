package com.example.agent.workflow;

import com.example.agent.model.DocumentAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 链式工作流
 * 按顺序执行多个步骤，每个步骤的输出作为下一步的输入
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChainWorkflow {
    
    private final ChatClient.Builder chatClientBuilder;
    
    /**
     * 通用链式执行
     */
    public String executeChain(String input, List<String> steps) {
        log.info("开始链式工作流，共 {} 个步骤", steps.size());
        
        ChatClient chatClient = chatClientBuilder.build();
        String currentOutput = input;
        
        for (int i = 0; i < steps.size(); i++) {
            String step = steps.get(i);
            log.info("执行步骤 {}/{}: {}", i + 1, steps.size(), step);
            
            String prompt = String.format("""
                Previous output: %s
                
                Current step: %s
                
                Process the previous output according to the current step.
                """, currentOutput, step);
            
            currentOutput = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            
            log.info("步骤 {} 完成，输出长度: {}", i + 1, currentOutput.length());
        }
        
        return currentOutput;
    }
    
    /**
     * 文档分析链
     * 步骤1：提取关键信息 → 步骤2：情感分析 → 步骤3：生成摘要
     */
    public DocumentAnalysis analyzeDocument(String documentContent) {
        log.info("开始文档分析链");
        long startTime = System.currentTimeMillis();
        
        ChatClient chatClient = chatClientBuilder.build();
        
        // 步骤1：提取关键信息
        log.info("步骤1：提取关键信息");
        String keyInfo = chatClient.prompt()
                .system("你是一个专业的信息提取专家。从文档中提取最重要的关键信息、数据和观点。")
                .user(documentContent)
                .call()
                .content();
        
        // 步骤2：情感分析
        log.info("步骤2：情感分析");
        String sentiment = chatClient.prompt()
                .system("你是一个情感分析专家。分析文本的情感倾向（积极、消极、中性）和情绪强度。")
                .user("请分析以下内容的情感：\n\n" + keyInfo)
                .call()
                .content();
        
        // 步骤3：生成摘要
        log.info("步骤3：生成摘要");
        String summary = chatClient.prompt()
                .system("你是一个专业的摘要生成专家。基于关键信息和情感分析，生成简洁全面的摘要。")
                .user(String.format("""
                    关键信息：
                    %s
                    
                    情感分析：
                    %s
                    
                    请生成一个综合摘要。
                    """, keyInfo, sentiment))
                .call()
                .content();
        
        long processingTime = System.currentTimeMillis() - startTime;
        log.info("文档分析链完成，耗时: {}ms", processingTime);
        
        return new DocumentAnalysis(keyInfo, sentiment, summary, processingTime);
    }
    
    /**
     * 内容创作链
     * 步骤1：头脑风暴 → 步骤2：大纲 → 步骤3：撰写 → 步骤4：润色
     */
    public String createContent(String topic) {
        log.info("开始内容创作链: {}", topic);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        // 步骤1：头脑风暴
        String brainstorm = chatClient.prompt()
                .system("你是一个创意专家。进行头脑风暴，列出关于主题的各种想法和角度。")
                .user("主题：" + topic)
                .call()
                .content();
        
        // 步骤2：创建大纲
        String outline = chatClient.prompt()
                .system("你是一个内容策划专家。基于头脑风暴的结果，创建一个结构清晰的文章大纲。")
                .user("头脑风暴结果：\n" + brainstorm)
                .call()
                .content();
        
        // 步骤3：撰写内容
        String draft = chatClient.prompt()
                .system("你是一个专业作家。根据大纲撰写详细的文章内容。")
                .user("大纲：\n" + outline)
                .call()
                .content();
        
        // 步骤4：润色
        String polished = chatClient.prompt()
                .system("你是一个编辑专家。润色文章，改进语言表达，确保逻辑清晰。")
                .user("草稿：\n" + draft)
                .call()
                .content();
        
        return polished;
    }
    
    /**
     * 代码审查链
     * 步骤1：语法检查 → 步骤2：性能分析 → 步骤3：安全审查 → 步骤4：建议总结
     */
    public String reviewCode(String code) {
        log.info("开始代码审查链");
        
        ChatClient chatClient = chatClientBuilder.build();
        
        // 步骤1：语法检查
        String syntaxCheck = chatClient.prompt()
                .system("你是一个代码审查专家。检查代码的语法、命名规范和代码风格。")
                .user("代码：\n" + code)
                .call()
                .content();
        
        // 步骤2：性能分析
        String performanceAnalysis = chatClient.prompt()
                .system("你是一个性能优化专家。分析代码的性能问题和优化建议。")
                .user("代码：\n" + code + "\n\n语法检查结果：\n" + syntaxCheck)
                .call()
                .content();
        
        // 步骤3：安全审查
        String securityReview = chatClient.prompt()
                .system("你是一个安全专家。审查代码的安全漏洞和风险。")
                .user("代码：\n" + code)
                .call()
                .content();
        
        // 步骤4：建议总结
        String summary = chatClient.prompt()
                .system("你是一个技术总监。综合所有审查结果，给出最终建议。")
                .user(String.format("""
                    语法检查：%s
                    
                    性能分析：%s
                    
                    安全审查：%s
                    
                    请给出综合建议。
                    """, syntaxCheck, performanceAnalysis, securityReview))
                .call()
                .content();
        
        return summary;
    }
}
