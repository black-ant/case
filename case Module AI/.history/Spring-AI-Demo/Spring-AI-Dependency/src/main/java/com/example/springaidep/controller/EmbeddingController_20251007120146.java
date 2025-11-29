package com.example.springaidep.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 嵌入模型控制器
 * 展示文本向量化功能
 */
@Slf4j
@RestController
@RequestMapping("/api/embedding")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingClient embeddingClient;

    /**
     * 单个文本嵌入
     */
    @PostMapping("/single")
    public Map<String, Object> embedSingle(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        log.info("嵌入文本: {}", text);
        
        EmbeddingResponse response = embeddingClient.embedForResponse(List.of(text));
        List<Double> embedding = response.getResult().getOutput();
        
        return Map.of(
            "text", text,
            "embedding", embedding,
            "dimensions", embedding.size(),
            "metadata", response.getMetadata()
        );
    }

    /**
     * 批量文本嵌入
     */
    @PostMapping("/batch")
    public Map<String, Object> embedBatch(@RequestBody Map<String, List<String>> request) {
        List<String> texts = request.get("texts");
        log.info("批量嵌入 {} 个文本", texts.size());
        
        EmbeddingResponse response = embeddingClient.embedForResponse(texts);
        
        return Map.of(
            "count", texts.size(),
            "embeddings", response.getResults().stream()
                .map(result -> result.getOutput())
                .toList(),
            "dimensions", response.getResult().getOutput().size()
        );
    }

    /**
     * 计算文本相似度
     */
    @PostMapping("/similarity")
    public Map<String, Object> calculateSimilarity(@RequestBody Map<String, String> request) {
        String text1 = request.get("text1");
        String text2 = request.get("text2");
        
        List<Double> embedding1 = embeddingClient.embed(text1);
        List<Double> embedding2 = embeddingClient.embed(text2);
        
        double similarity = cosineSimilarity(embedding1, embedding2);
        
        return Map.of(
            "text1", text1,
            "text2", text2,
            "similarity", similarity,
            "interpretation", similarity > 0.8 ? "非常相似" : 
                            similarity > 0.6 ? "相似" : 
                            similarity > 0.4 ? "有些相似" : "不太相似"
        );
    }

    /**
     * 计算余弦相似度
     */
    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            norm1 += Math.pow(vec1.get(i), 2);
            norm2 += Math.pow(vec2.get(i), 2);
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "service", "EmbeddingModel",
            "message", "Embedding service is running"
        );
    }
}
