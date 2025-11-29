package com.example.customerservice.controller;

import com.example.customerservice.config.VectorStorePersistence;
import com.example.customerservice.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识库管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KnowledgeController {
    
    private final KnowledgeBaseService knowledgeBaseService;
    private final VectorStorePersistence vectorStorePersistence;
    
    /**
     * 搜索知识
     */
    @GetMapping("/search")
    public Map<String, Object> searchKnowledge(
            @RequestParam String query,
            @RequestParam(defaultValue = "3") int topK) {
        
        log.info("搜索知识: query={}, topK={}", query, topK);
        
        List<Document> results = knowledgeBaseService.searchKnowledge(query, topK);
        
        Map<String, Object> response = new HashMap<>();
        response.put("query", query);
        response.put("topK", topK);
        response.put("results", results.stream().map(doc -> {
            Map<String, Object> item = new HashMap<>();
            item.put("content", doc.getContent());
            item.put("metadata", doc.getMetadata());
            return item;
        }).toList());
        
        return response;
    }
    
    /**
     * 添加知识
     */
    @PostMapping("/add")
    public Map<String, Object> addKnowledge(@RequestBody Map<String, Object> request) {
        String content = (String) request.get("content");
        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = (Map<String, Object>) request.getOrDefault("metadata", new HashMap<>());
        
        log.info("添加知识: {}", content.substring(0, Math.min(50, content.length())));
        
        knowledgeBaseService.addKnowledge(content, metadata);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "知识添加成功并已保存到文件");
        
        return response;
    }
    
    /**
     * 手动保存向量存储
     */
    @PostMapping("/save")
    public Map<String, Object> saveVectorStore() {
        log.info("手动触发向量存储保存");
        
        vectorStorePersistence.manualSave();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "向量存储已保存到 data/vector-store.json");
        
        return response;
    }
    
    /**
     * 获取知识库统计信息
     */
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("storageFile", "data/vector-store.json");
        stats.put("storageType", "SimpleVectorStore (File-based)");
        stats.put("description", "向量数据持久化到本地 JSON 文件，类似 SQLite");
        
        return stats;
    }
}
