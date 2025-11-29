package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RAG 服务
 * 实现各种 RAG 模式
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RAGService {
    
    private final VectorStore vectorStore;
    private final ChatClient.Builder chatClientBuilder;
    
    @Value("${rag.retrieval.top-k:5}")
    private int topK;
    
    @Value("${rag.retrieval.similarity-threshold:0.7}")
    private double similarityThreshold;
    
    /**
     * 1. 基础 RAG 查询
     */
    public Map<String, Object> basicRAG(String query) {
        log.info("基础 RAG 查询: {}", query);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        // 使用 QuestionAnswerAdvisor 自动检索相关文档
        String response = chatClient.prompt()
                .user(query)
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .call()
                .content();
        
        // 获取检索到的文档
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(query).withTopK(topK)
        );
        
        return Map.of(
                "query", query,
                "response", response,
                "retrieved_documents", documents.size(),
                "sources", documents.stream()
                        .map(doc -> doc.getMetadata().get("source"))
                        .distinct()
                        .collect(Collectors.toList())
        );
    }
    
    /**
     * 2. 带过滤的 RAG
     */
    public Map<String, Object> filteredRAG(String query, String source) {
        log.info("带过滤的 RAG 查询: query={}, source={}", query, source);
        
        // 构建带过滤条件的搜索请求
        SearchRequest searchRequest = SearchRequest.query(query)
                .withTopK(topK)
                .withSimilarityThreshold(similarityThreshold)
                .withFilterExpression("source == '" + source + "'");
        
        QuestionAnswerAdvisor advisor = new QuestionAnswerAdvisor(vectorStore, searchRequest);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        String response = chatClient.prompt()
                .user(query)
                .advisors(advisor)
                .call()
                .content();
        
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        
        return Map.of(
                "query", query,
                "filter", "source=" + source,
                "response", response,
                "retrieved_documents", documents.size()
        );
    }
    
    /**
     * 3. 多步骤 RAG
     */
    public Map<String, Object> multiStepRAG(String complexQuery) {
        log.info("多步骤 RAG 查询: {}", complexQuery);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        // 第一步：查询分解
        String decomposedQueries = chatClient.prompt()
                .system("将这个复杂问题分解成 3-5 个简单的子问题，每行一个问题")
                .user(complexQuery)
                .call()
                .content();
        
        log.info("分解后的子问题:\n{}", decomposedQueries);
        
        // 第二步：分别检索
        List<String> subQueries = parseQueries(decomposedQueries);
        List<Document> allRelevantDocs = new ArrayList<>();
        
        for (String subQuery : subQueries) {
            List<Document> docs = vectorStore.similaritySearch(
                    SearchRequest.query(subQuery).withTopK(3)
            );
            allRelevantDocs.addAll(docs);
        }
        
        // 去重
        allRelevantDocs = allRelevantDocs.stream()
                .distinct()
                .collect(Collectors.toList());
        
        log.info("检索到 {} 个相关文档", allRelevantDocs.size());
        
        // 第三步：综合回答
        String context = allRelevantDocs.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n\n"));
        
        String response = chatClient.prompt()
                .system("基于以下上下文回答问题:\n\n" + context)
                .user(complexQuery)
                .call()
                .content();
        
        return Map.of(
                "query", complexQuery,
                "sub_queries", subQueries,
                "retrieved_documents", allRelevantDocs.size(),
                "response", response
        );
    }
    
    /**
     * 4. 相似度搜索（不生成回答）
     */
    public Map<String, Object> similaritySearch(String query, int topK) {
        log.info("相似度搜索: query={}, topK={}", query, topK);
        
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(query)
                        .withTopK(topK)
                        .withSimilarityThreshold(similarityThreshold)
        );
        
        List<Map<String, Object>> results = documents.stream()
                .map(doc -> Map.of(
                        "content", doc.getContent(),
                        "metadata", doc.getMetadata(),
                        "score", doc.getMetadata().getOrDefault("distance", "N/A")
                ))
                .collect(Collectors.toList());
        
        return Map.of(
                "query", query,
                "top_k", topK,
                "results", results
        );
    }
    
    /**
     * 5. 带引用的 RAG
     */
    public Map<String, Object> ragWithCitations(String query) {
        log.info("带引用的 RAG 查询: {}", query);
        
        // 检索相关文档
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(query).withTopK(topK)
        );
        
        // 构建带编号的上下文
        StringBuilder contextBuilder = new StringBuilder();
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            contextBuilder.append(String.format("[%d] %s\n来源: %s\n\n",
                    i + 1,
                    doc.getContent(),
                    doc.getMetadata().get("source")));
        }
        
        ChatClient chatClient = chatClientBuilder.build();
        
        String response = chatClient.prompt()
                .system("基于以下编号的文档回答问题，并在回答中引用文档编号（如 [1]、[2]）:\n\n" + contextBuilder)
                .user(query)
                .call()
                .content();
        
        return Map.of(
                "query", query,
                "response", response,
                "citations", documents.stream()
                        .map(doc -> Map.of(
                                "source", doc.getMetadata().get("source"),
                                "content_preview", doc.getContent().substring(0, Math.min(100, doc.getContent().length())) + "..."
                        ))
                        .collect(Collectors.toList())
        );
    }
    
    /**
     * 解析查询列表
     */
    private List<String> parseQueries(String queriesText) {
        return queriesText.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("#"))
                .map(line -> line.replaceAll("^\\d+\\.\\s*", ""))  // 移除编号
                .collect(Collectors.toList());
    }
}
