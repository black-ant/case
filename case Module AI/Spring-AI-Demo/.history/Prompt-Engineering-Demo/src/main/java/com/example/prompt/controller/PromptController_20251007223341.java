package com.example.prompt.controller;

import com.example.prompt.model.AnalysisRequest;
import com.example.prompt.service.ConversationMemoryService;
import com.example.prompt.service.PromptTemplateService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 提示工程控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/prompt")
@RequiredArgsConstructor
public class PromptController {
    
    private final PromptTemplateService promptTemplateService;
    private final ConversationMemoryService conversationMemoryService;
    
    @Data
    public static class ChatRequest {
        private String sessionId;
        private String message;
    }
    
    @Data
    public static class MultiTurnRequest {
        private String topic;
        private List<String> questions;
    }
    
    @Data
    public static class RolePlayRequest {
        private String role;
        private String scenario;
        private String userInput;
    }
    
    @Data
    public static class StructuredRequest {
        private String topic;
        private String format;
    }
    
    @Data
    public static class FewShotRequest {
        private String task;
        private List<Map<String, String>> examples;
        private String input;
    }
    
    // ==================== 提示模板 ====================
    
    /**
     * 1. 复杂分析模板
     */
    @PostMapping("/template/analysis")
    public Map<String, Object> generateAnalysis(@RequestBody AnalysisRequest request) {
        String result = promptTemplateService.generateAnalysis(request);
        return Map.of(
                "request", request,
                "analysis", result
        );
    }
    
    /**
     * 2. 多轮对话模板
     */
    @PostMapping("/template/multi-turn")
    public Map<String, Object> multiTurnConversation(@RequestBody MultiTurnRequest request) {
        return promptTemplateService.multiTurnConversation(
                request.getTopic(),
                request.getQuestions()
        );
    }
    
    /**
     * 3. 角色扮演模板
     */
    @PostMapping("/template/role-play")
    public Map<String, Object> rolePlay(@RequestBody RolePlayRequest request) {
        String result = promptTemplateService.rolePlayTemplate(
                request.getRole(),
                request.getScenario(),
                request.getUserInput()
        );
        return Map.of(
                "role", request.getRole(),
                "response", result
        );
    }
    
    /**
     * 4. 结构化输出模板
     */
    @PostMapping("/template/structured")
    public Map<String, Object> structuredOutput(@RequestBody StructuredRequest request) {
        String result = promptTemplateService.structuredOutputTemplate(
                request.getTopic(),
                request.getFormat()
        );
        return Map.of(
                "topic", request.getTopic(),
                "output", result
        );
    }
    
    /**
     * 5. Few-Shot 学习模板
     */
    @PostMapping("/template/few-shot")
    public Map<String, Object> fewShot(@RequestBody FewShotRequest request) {
        String result = promptTemplateService.fewShotTemplate(
                request.getTask(),
                request.getExamples(),
                request.getInput()
        );
        return Map.of(
                "task", request.getTask(),
                "output", result
        );
    }
    
    /**
     * 6. Chain-of-Thought 模板
     */
    @PostMapping("/template/chain-of-thought")
    public Map<String, Object> chainOfThought(@RequestBody ChatRequest request) {
        String result = promptTemplateService.chainOfThoughtTemplate(request.getMessage());
        return Map.of(
                "problem", request.getMessage(),
                "solution", result
        );
    }
    
    // ==================== 对话记忆 ====================
    
    /**
     * 7. 带记忆的对话
     */
    @PostMapping("/memory/chat")
    public Map<String, Object> chatWithMemory(@RequestBody ChatRequest request) {
        return conversationMemoryService.chatWithMemory(
                request.getSessionId(),
                request.getMessage()
        );
    }
    
    /**
     * 8. 获取对话历史
     */
    @GetMapping("/memory/history/{sessionId}")
    public Map<String, Object> getHistory(@PathVariable String sessionId) {
        return conversationMemoryService.getConversationHistory(sessionId);
    }
    
    /**
     * 9. 清除对话记忆
     */
    @DeleteMapping("/memory/clear/{sessionId}")
    public Map<String, Object> clearMemory(@PathVariable String sessionId) {
        return conversationMemoryService.clearMemory(sessionId);
    }
    
    /**
     * 10. 压缩对话记忆
     */
    @PostMapping("/memory/compress/{sessionId}")
    public Map<String, Object> compressMemory(@PathVariable String sessionId) {
        return conversationMemoryService.compressMemory(sessionId);
    }
    
    /**
     * 11. 列出所有会话
     */
    @GetMapping("/memory/sessions")
    public Map<String, Object> listSessions() {
        return conversationMemoryService.listSessions();
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "Prompt Engineering Demo"
        );
    }
}
