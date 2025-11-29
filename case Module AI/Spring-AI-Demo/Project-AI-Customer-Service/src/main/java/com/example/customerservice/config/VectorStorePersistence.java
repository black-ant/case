package com.example.customerservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.io.File;

/**
 * 向量存储持久化组件
 * 在应用关闭时自动保存向量数据到文件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VectorStorePersistence {
    
    private final VectorStore vectorStore;
    
    private static final String VECTOR_STORE_FILE = "data/vector-store.json";
    
    /**
     * 应用关闭时保存向量存储
     */
    @PreDestroy
    public void saveVectorStore() {
        if (vectorStore instanceof SimpleVectorStore simpleVectorStore) {
            try {
                File file = new File(VECTOR_STORE_FILE);
                log.info("保存向量存储到文件: {}", VECTOR_STORE_FILE);
                simpleVectorStore.save(file);
                log.info("向量存储保存成功，文件大小: {} KB", file.length() / 1024);
            } catch (Exception e) {
                log.error("保存向量存储失败", e);
            }
        } else {
            log.warn("当前 VectorStore 不是 SimpleVectorStore，跳过持久化");
        }
    }
    
    /**
     * 手动保存向量存储（可选）
     */
    public void manualSave() {
        log.info("手动触发向量存储保存");
        saveVectorStore();
    }
}
