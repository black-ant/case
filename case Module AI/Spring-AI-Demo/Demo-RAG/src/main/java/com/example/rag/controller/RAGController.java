package com.example.rag.controller;

import com.example.rag.service.RAGService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * RAG 查询控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RAGController {
    
    private final RAGService ragService;
    
    @Data
    public static class QueryRequest {
        private String query;
        private String source;
        private Integer topK;
    }
    
    /**
     * 1. 基础 RAG 查询
     */
    @PostMapping("/query")
    public Map<String, Object> query(@RequestBody QueryRequest request) {
        return ragService.basicRAG(request.getQuery());
    }
    
    /**
     * 2. 带过滤的 RAG 查询
     */
    @PostMapping("/query/filtered")
    public Map<String, Object> filteredQuery(@RequestBody QueryRequest request) {
        return ragService.filteredRAG(request.getQuery(), request.getSource());
    }
    
    /**
     * 3. 多步骤 RAG 查询
     */
    @PostMapping("/query/multi-step")
    public Map<String, Object> multiStepQuery(@RequestBody QueryRequest request) {
        return ragService.multiStepRAG(request.getQuery());
    }
    
    /**
     * 4. 相似度搜索
     */
    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody QueryRequest request) {
        int topK = request.getTopK() != null ? request.getTopK() : 5;
        return ragService.similaritySearch(request.getQuery(), topK);
    }
    
    /**
     * 5. 带引用的 RAG 查询
     */
    @PostMapping("/query/with-citations")
    public Map<String, Object> queryWithCitations(@RequestBody QueryRequest request) {
        return ragService.ragWithCitations(request.getQuery());
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "RAG Demo"
        );
    }
}
