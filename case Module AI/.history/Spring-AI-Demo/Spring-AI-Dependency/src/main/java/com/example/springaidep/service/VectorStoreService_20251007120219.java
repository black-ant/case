package com.example.springaidep.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 向量存储服务
 * 封装向量数据库操作
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;
    private final EmbeddingClient embeddingClient;

    /**
     * 添加单个文档
     */
    public void addDocument(String content, String id) {
        String docId = id != null ? id : UUID.randomUUID().toString();
        
        // 创建文档
        Document document = new Document(docId, content);
        
        // 生成嵌入向量
        List<Double> embedding = embeddingClient.embed(content);
        document.setEmbedding(embedding);
        
        // 保存到向量存储
        vectorStore.add(List.of(document));
        
        log.info("文档已添加: id={}, contentLength={}", docId, content.length());
    }

    /**
     * 批量添加文档
     */
    public void addDocuments(List<String> contents) {
        List<Document> documents = contents.stream()
            .map(content -> {
                String id = UUID.randomUUID().toString();
                Document doc = new Document(id, content);
                List<Double> embedding = embeddingClient.embed(content);
                doc.setEmbedding(embedding);
                return doc;
            })
            .toList();
        
        vectorStore.add(documents);
        log.info("批量添加 {} 个文档", documents.size());
    }

    /**
     * 相似度搜索
     */
    public List<Document> search(String query, int topK) {
        SearchRequest request = SearchRequest.query(query)
            .withTopK(topK);
        
        List<Document> results = vectorStore.similaritySearch(request);
        log.info("搜索完成: query={}, topK={}, results={}", query, topK, results.size());
        
        return results;
    }

    /**
     * 删除文档
     */
    public void deleteDocument(String id) {
        vectorStore.delete(List.of(id));
        log.info("文档已删除: id={}", id);
    }

    /**
     * 清空向量存储
     */
    public void clear() {
        // 注意: 不是所有向量存储都支持此操作
        log.warn("清空向量存储操作需要根据具体实现调整");
    }
}
