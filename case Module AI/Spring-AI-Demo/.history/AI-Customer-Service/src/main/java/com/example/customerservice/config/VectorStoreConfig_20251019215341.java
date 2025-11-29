package com.example.customerservice.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 向量存储配置 - 使用 SiliconFlow Embeddings API
 */
@Configuration
public class VectorStoreConfig {
    
    @Value("${embeddings.api-key}")
    private String embeddingsApiKey;
    
    @Value("${embeddings.base-url}")
    private String embeddingsBaseUrl;
    
    @Value("${embeddings.model}")
    private String embeddingsModel;
    
    /**
     * Embeddings 模型 - 使用 SiliconFlow API
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        // 创建 OpenAI API 实例，使用自定义的 base-url
        OpenAiApi openAiApi = new OpenAiApi(embeddingsBaseUrl, embeddingsApiKey);
        
        // 配置 Embeddings 选项
        OpenAiEmbeddingOptions options = OpenAiEmbeddingOptions.builder()
            .withModel(embeddingsModel)
            .build();
        
        return new OpenAiEmbeddingModel(openAiApi, options);
    }
    
    /**
     * 向量存储 - 使用简单的内存存储
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }
}
