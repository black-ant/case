package com.example.agent.controller;

import com.example.agent.model.DocumentAnalysis;
import com.example.agent.model.MarketAnalysis;
import com.example.agent.workflow.ChainWorkflow;
import com.example.agent.workflow.ParallelWorkflow;
import com.example.agent.workflow.RoutingWorkflow;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 工作流控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/workflow")
@RequiredArgsConstructor
public class WorkflowController {
    
    private final ChainWorkflow chainWorkflow;
    private final ParallelWorkflow parallelWorkflow;
    private final RoutingWorkflow routingWorkflow;
    
    @Data
    public static class ChainRequest {
        private String input;
        private List<String> steps;
    }
    
    @Data
    public static class ParallelRequest {
        private String input;
        private List<String> perspectives;
    }
    
    @Data
    public static class DocumentRequest {
        private String content;
    }
    
    @Data
    public static class QueryRequest {
        private String query;
    }
    
    @Data
    public static class MarketRequest {
        private String marketData;
    }
    
    // ==================== 链式工作流 ====================
    
    /**
     * 1. 通用链式执行
     */
    @PostMapping("/chain/execute")
    public Map<String, Object> executeChain(@RequestBody ChainRequest request) {
        String result = chainWorkflow.executeChain(request.getInput(), request.getSteps());
        return Map.of(
                "input", request.getInput(),
                "steps", request.getSteps(),
                "result", result
        );
    }
    
    /**
     * 2. 文档分析链
     */
    @PostMapping("/chain/analyze-document")
    public DocumentAnalysis analyzeDocument(@RequestBody DocumentRequest request) {
        return chainWorkflow.analyzeDocument(request.getContent());
    }
    
    /**
     * 3. 内容创作链
     */
    @PostMapping("/chain/create-content")
    public Map<String, Object> createContent(@RequestBody QueryRequest request) {
        String result = chainWorkflow.createContent(request.getQuery());
        return Map.of(
                "topic", request.getQuery(),
                "content", result
        );
    }
    
    /**
     * 4. 代码审查链
     */
    @PostMapping("/chain/review-code")
    public Map<String, Object> reviewCode(@RequestBody DocumentRequest request) {
        String result = chainWorkflow.reviewCode(request.getContent());
        return Map.of(
                "code", request.getContent(),
                "review", result
        );
    }
    
    // ==================== 并行工作流 ====================
    
    /**
     * 5. 通用并行分析
     */
    @PostMapping("/parallel/analyze")
    public Map<String, Object> parallelAnalyze(@RequestBody ParallelRequest request) {
        Map<String, String> results = parallelWorkflow.parallelAnalysis(
                request.getInput(),
                request.getPerspectives()
        );
        return Map.of(
                "input", request.getInput(),
                "perspectives", request.getPerspectives(),
                "results", results
        );
    }
    
    /**
     * 6. 市场分析
     */
    @PostMapping("/parallel/analyze-market")
    public MarketAnalysis analyzeMarket(@RequestBody MarketRequest request) {
        return parallelWorkflow.analyzeMarket(request.getMarketData());
    }
    
    /**
     * 7. 产品评估
     */
    @PostMapping("/parallel/evaluate-product")
    public Map<String, Object> evaluateProduct(@RequestBody DocumentRequest request) {
        Map<String, String> results = parallelWorkflow.evaluateProduct(request.getContent());
        return Map.of(
                "product", request.getContent(),
                "evaluations", results
        );
    }
    
    // ==================== 路由工作流 ====================
    
    /**
     * 8. 智能路由查询
     */
    @PostMapping("/routing/query")
    public Map<String, Object> routeQuery(@RequestBody QueryRequest request) {
        return routingWorkflow.routeQuery(request.getQuery());
    }
    
    /**
     * 9. 复杂度路由
     */
    @PostMapping("/routing/by-complexity")
    public Map<String, Object> routeByComplexity(@RequestBody QueryRequest request) {
        return routingWorkflow.routeByComplexity(request.getQuery());
    }
    
    /**
     * 10. 情感路由
     */
    @PostMapping("/routing/by-emotion")
    public Map<String, Object> routeByEmotion(@RequestBody QueryRequest request) {
        return routingWorkflow.routeByEmotion(request.getQuery());
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "Agent Workflow Demo"
        );
    }
}
