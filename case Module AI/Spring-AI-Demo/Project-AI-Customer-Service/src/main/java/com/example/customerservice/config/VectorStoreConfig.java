package com.example.customerservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 向量存储配置 - 使用 SiliconFlow Embeddings API + 文件持久化
 */
@Slf4j
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
        
        // 创建 OpenAiEmbeddingModel，传入 API 和选项
        return new OpenAiEmbeddingModel(openAiApi, 
            org.springframework.ai.document.MetadataMode.EMBED, 
            options);
    }
    
    /**
     * 向量存储 - 使用简单的内存存储 + 文件持久化
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);
        
        // 定义持久化文件路径
        String vectorStoreFile = "data/vector-store.json";
        File file = new File(vectorStoreFile);
        
        // 如果文件存在，从文件加载向量数据
        if (file.exists()) {
            try {
                log.info("从文件加载向量存储: {}", vectorStoreFile);
                vectorStore.load(file);
                log.info("向量存储加载成功");
            } catch (Exception e) {
                log.error("加载向量存储失败，将使用空存储", e);
            }
        } else {
            log.info("向量存储文件不存在，将创建新的存储: {}", vectorStoreFile);
            // 确保目录存在
            try {
                Path dataDir = Paths.get("data");
                if (!Files.exists(dataDir)) {
                    Files.createDirectories(dataDir);
                    log.info("创建数据目录: {}", dataDir);
                }
            } catch (Exception e) {
                log.error("创建数据目录失败", e);
            }
        }
        
        return vectorStore;
    }
}
