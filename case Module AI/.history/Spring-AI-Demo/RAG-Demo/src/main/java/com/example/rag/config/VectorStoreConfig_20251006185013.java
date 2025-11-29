package com.example.rag.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 向量存储配置
 * 支持多种向量数据库
 */
@Slf4j
@Configuration
public class VectorStoreConfig {
    
    /**
     * Simple Vector Store（内存存储）
     * 适合开发和测试环境
     */
    @Bean
    @ConditionalOnProperty(name = "rag.vector-store.type", havingValue = "simple", matchIfMissing = true)
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        log.info("初始化 Simple Vector Store（内存存储）");
        return new SimpleVectorStore(embeddingModel);
    }
    
    /**
     * PGVector Store（PostgreSQL + pgvector 扩展）
     * 适合生产环境
     */
    @Bean
    @ConditionalOnProperty(name = "rag.vector-store.type", havingValue = "pgvector")
    public VectorStore pgVectorStore(
            JdbcTemplate jdbcTemplate,
            EmbeddingModel embeddingModel,
            @Value("${rag.vector-store.schema-name:ai_vectors}") String schemaName,
            @Value("${rag.vector-store.table-name:document_embeddings}") String tableName,
            @Value("${rag.vector-store.dimensions:1536}") int dimensions) {
        
        log.info("初始化 PGVector Store: schema={}, table={}, dimensions={}", 
                schemaName, tableName, dimensions);
        
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .withSchemaName(schemaName)
                .withTableName(tableName)
                .withDimensions(dimensions)
                .withDistanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .withIndexType(PgVectorStore.PgIndexType.HNSW)
                .withRemoveExistingVectorStoreTable(false)
                .withInitializeSchema(true)
                .build();
    }
}
