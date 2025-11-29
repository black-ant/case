package com.example.customerservice.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 向量存储配置 - 使用简单的内存存储（不需要真实的 Embeddings API）
 */
@Configuration
public class VectorStoreConfig {
    
    /**
     * 简单的内存向量存储实现（不依赖外部 Embeddings API）
     */
    @Bean
    public VectorStore vectorStore() {
        return new VectorStore() {
            private final List<Document> documents = new ArrayList<>();
            
            @Override
            public void add(List<Document> documents) {
                this.documents.addAll(documents);
            }
            
            @Override
            public Optional<Boolean> delete(List<String> idList) {
                return Optional.of(true);
            }
            
            @Override
            public List<Document> similaritySearch(SearchRequest request) {
                // 简单的关键词匹配（不使用向量相似度）
                String query = request.getQuery().toLowerCase();
                return documents.stream()
                    .filter(doc -> doc.getContent().toLowerCase().contains(query))
                    .limit(request.getTopK())
                    .toList();
            }
            
            @Override
            public List<Document> similaritySearch(String query) {
                return similaritySearch(SearchRequest.query(query).withTopK(3));
            }
        };
    }
    
    /**
     * 提供一个空的 EmbeddingModel（如果其他地方需要）
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        return new EmbeddingModel() {
            @Override
            public EmbeddingResponse call(org.springframework.ai.embedding.EmbeddingRequest request) {
                // 返回空的 embedding（不实际调用 API）
                return new EmbeddingResponse(List.of());
            }
            
            @Override
            public float[] embed(Document document) {
                return new float[0];
            }
        };
    }
}
