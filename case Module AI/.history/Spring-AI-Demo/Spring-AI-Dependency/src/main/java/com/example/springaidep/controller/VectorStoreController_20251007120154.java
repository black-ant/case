package com.example.springaidep.controller;

import com.example.springaidep.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 向量存储控制器
 * 展示向量数据库的使用
 */
@Slf4j
@RestController
@RequestMapping("/api/vector")
@RequiredArgsConstructor
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;

    /**
     * 添加文档到向量存储
     */
    @PostMapping("/add")
    public Map<String, Object> addDocument(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        String id = request.getOrDefault("id", null);
        
        log.info("添加文档到向量存储: {}", content.substring(0, Math.min(50, content.length())));
        
        vectorStoreService.addDocument(content, id);
        
        return Map.of(
            "status", "success",
            "message", "文档已添加到向量存储",
            "contentLength", content.length()
        );
    }

    /**
     * 批量添加文档
     */
    @PostMapping("/add-batch")
    public Map<String, Object> addDocuments(@RequestBody Map<String, List<String>> request) {
        List<String> contents = request.get("contents");
        
        log.info("批量添加 {} 个文档", contents.size());
        
        vectorStoreService.addDocuments(contents);
        
        return Map.of(
            "status", "success",
            "message", "文档已批量添加",
            "count", contents.size()
        );
    }

    /**
     * 相似度搜索
     */
    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody Map<String, Object> request) {
        String query = (String) request.get("query");
        int topK = request.containsKey("topK") 
            ? ((Number) request.get("topK")).intValue() 
            : 5;
        
        log.info("向量搜索: {}, topK={}", query, topK);
        
        List<Document> results = vectorStoreService.search(query, topK);
        
        return Map.of(
            "query", query,
            "topK", topK,
            "results", results.stream()
                .map(doc -> Map.of(
                    "id", doc.getId(),
                    "content", doc.getContent(),
                    "metadata", doc.getMetadata()
                ))
                .toList(),
            "count", results.size()
        );
    }

    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteDocument(@PathVariable String id) {
        log.info("删除文档: {}", id);
        
        vectorStoreService.deleteDocument(id);
        
        return Map.of(
            "status", "success",
            "message", "文档已删除",
            "id", id
        );
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "service", "VectorStore",
            "message", "Vector store service is running"
        );
    }
}
