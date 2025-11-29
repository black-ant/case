package com.example.rag.config;

import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RAG 相关配置
 */
@Configuration
public class RAGConfig {
    
    /**
     * 文档分块器
     * 将长文档切分成适合嵌入的小块
     */
    @Bean
    public TokenTextSplitter tokenTextSplitter(
            @Value("${rag.chunking.chunk-size:500}") int chunkSize,
            @Value("${rag.chunking.chunk-overlap:100}") int chunkOverlap,
            @Value("${rag.chunking.min-chunk-size:50}") int minChunkSize,
            @Value("${rag.chunking.max-chunk-size:2000}") int maxChunkSize) {
        
        return new TokenTextSplitter(
                chunkSize,      // 每块的大小
                chunkOverlap,   // 块之间的重叠
                minChunkSize,   // 最小块大小
                maxChunkSize,   // 最大块大小
                true            // 保留分隔符
        );
    }
}
