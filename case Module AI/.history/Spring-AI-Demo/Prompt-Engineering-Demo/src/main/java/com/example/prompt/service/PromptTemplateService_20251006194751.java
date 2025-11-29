package com.example.prompt.service;

import com.example.prompt.model.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 提示模板服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromptTemplateService {
    
    private final ChatClient.Builder chatClientBuilder;
    
    /**
     * 复杂分析模板
     */
    private static final String ANALYSIS_TEMPLATE = """
        你是一个拥有 {experience} 年经验的专业 {domain} 分析师。
        
        背景信息：
        {context}
        
        任务：
        {task}
        
        要求：
        {requirements}
        
        输出格式：
        {output_format}
        
        额外约束：
        {constraints}
        
        请根据以上信息进行专业分析。
        """;
    
    /**
     * 1. 使用复杂模板生成分析
     */
    public String generateAnalysis(AnalysisRequest request) {
        log.info("生成分析: domain={}, task={}", request.getDomain(), request.getTask());
        
        PromptTemplate template = new PromptTemplate(ANALYSIS_TEMPLATE);
        
        // 格式化要求列表
        String requirementsList = request.getRequirements().stream()
                .map(req -> "- " + req)
                .collect(Collectors.joining("\n"));
        
        Map<String, Object> variables = Map.of(
                "domain", request.getDomain(),
                "experience", request.getExperienceLevel(),
                "context", request.getContext(),
                "task", request.getTask(),
                "requirements", requirementsList,
                "output_format", request.getOutputFormat(),
                "constraints", request.getConstraints()
        );
        
        Prompt prompt = template.create(variables);
        
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(prompt).call().content();
    }
    
    /**
     * 2. 多轮对话模板
     */
    public Map<String, Object> multiTurnConversation(String topic, List<String> questions) {
        log.info("开始多轮对话: topic={}, questions={}", topic, questions.size());
        
        List<Message> messages = new ArrayList<>();
        List<Map<String, String>> conversation = new ArrayList<>();
        
        // 系统消息
        String systemPrompt = String.format("""
            你正在进行一次关于 %s 的深入访谈。
            根据回答提出后续问题。
            在整个对话过程中保持上下文连贯。
            """, topic);
        
        messages.add(new SystemMessage(systemPrompt));
        
        ChatClient chatClient = chatClientBuilder.build();
        String currentResponse = "";
        
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            log.info("问题 {}/{}: {}", i + 1, questions.size(), question);
            
            messages.add(new UserMessage(question));
            
            Prompt prompt = new Prompt(messages);
            ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
            currentResponse = response.getResult().getOutput().getContent();
            
            messages.add(new AssistantMessage(currentResponse));
            
            conversation.add(Map.of(
                    "question", question,
                    "answer", currentResponse
            ));
        }
        
        return Map.of(
                "topic", topic,
                "conversation", conversation,
                "final_answer", currentResponse
        );
    }
    
    /**
     * 3. 角色扮演模板
     */
    public String rolePlayTemplate(String role, String scenario, String userInput) {
        log.info("角色扮演: role={}, scenario={}", role, scenario);
        
        String template = """
            你现在扮演：{role}
            
            场景设定：
            {scenario}
            
            用户输入：
            {user_input}
            
            请完全进入角色，以该角色的身份、语气和专业知识进行回应。
            """;
        
        PromptTemplate promptTemplate = new PromptTemplate(template);
        
        Map<String, Object> variables = Map.of(
                "role", role,
                "scenario", scenario,
                "user_input", userInput
        );
        
        Prompt prompt = promptTemplate.create(variables);
        
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(prompt).call().content();
    }
    
    /**
     * 4. 结构化输出模板
     */
    public String structuredOutputTemplate(String topic, String format) {
        log.info("结构化输出: topic={}, format={}", topic, format);
        
        String template = """
            主题：{topic}
            
            请按照以下格式输出：
            {format}
            
            确保输出严格遵循指定格式，便于解析。
            """;
        
        PromptTemplate promptTemplate = new PromptTemplate(template);
        
        Map<String, Object> variables = Map.of(
                "topic", topic,
                "format", format
        );
        
        Prompt prompt = promptTemplate.create(variables);
        
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(prompt).call().content();
    }
    
    /**
     * 5. Few-Shot 学习模板
     */
    public String fewShotTemplate(String task, List<Map<String, String>> examples, String input) {
        log.info("Few-Shot 学习: task={}, examples={}", task, examples.size());
        
        StringBuilder examplesText = new StringBuilder();
        for (int i = 0; i < examples.size(); i++) {
            Map<String, String> example = examples.get(i);
            examplesText.append(String.format("""
                示例 %d:
                输入：%s
                输出：%s
                
                """, i + 1, example.get("input"), example.get("output")));
        }
        
        String template = """
            任务：{task}
            
            {examples}
            
            现在请处理以下输入：
            {input}
            
            输出：
            """;
        
        PromptTemplate promptTemplate = new PromptTemplate(template);
        
        Map<String, Object> variables = Map.of(
                "task", task,
                "examples", examplesText.toString(),
                "input", input
        );
        
        Prompt prompt = promptTemplate.create(variables);
        
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(prompt).call().content();
    }
    
    /**
     * 6. Chain-of-Thought 模板
     */
    public String chainOfThoughtTemplate(String problem) {
        log.info("Chain-of-Thought: problem={}", problem);
        
        String template = """
            问题：{problem}
            
            请使用思维链（Chain-of-Thought）方法解决这个问题：
            
            1. 首先，理解问题的关键点
            2. 然后，分步骤思考解决方案
            3. 对每一步进行推理
            4. 最后，给出最终答案
            
            请详细展示你的思考过程。
            """;
        
        PromptTemplate promptTemplate = new PromptTemplate(template);
        
        Map<String, Object> variables = Map.of("problem", problem);
        
        Prompt prompt = promptTemplate.create(variables);
        
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(prompt).call().content();
    }
}
