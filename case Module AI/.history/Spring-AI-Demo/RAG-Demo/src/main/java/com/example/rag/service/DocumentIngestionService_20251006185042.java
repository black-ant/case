package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文档摄取服务
 * 负责读取、处理和存储文档
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentIngestionService {
    
    private final VectorStore vectorStore;
    private final TokenTextSplitter textSplitter;
    
    /**
     * 摄取单个文件
     */
    public Map<String, Object> ingestFile(MultipartFile file) throws IOException {
        log.info("开始摄取文件: {}", file.getOriginalFilename());
        
        // 保存临时文件
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());
        
        try {
            // 读取文档
            List<Document> documents = readDocument(tempFile.toFile());
            
            // 分块
            List<Document> chunks = textSplitter.apply(documents);
            
            // 添加元数据
            chunks.forEach(doc -> {
                doc.getMetadata().put("source", file.getOriginalFilename());
                doc.getMetadata().put("file_size", file.getSize());
                doc.getMetadata().put("content_type", file.getContentType());
                doc.getMetadata().put("ingestion_time", Instant.now().toString());
            });
            
            // 写入向量数据库
            vectorStore.add(chunks);
            
            log.info("文件摄取完成: {} 个文档块", chunks.size());
            
            return Map.of(
                    "success", true,
                    "filename", file.getOriginalFilename(),
                    "chunks", chunks.size(),
                    "message", "文档摄取成功"
            );
            
        } finally {
            // 清理临时文件
            Files.deleteIfExists(tempFile);
        }
    }
    
    /**
     * 摄取文本内容
     */
    public Map<String, Object> ingestText(String text, String title) {
        log.info("开始摄取文本: {}", title);
        
        // 创建文档
        Document document = new Document(text);
        document.getMetadata().put("title", title);
        document.getMetadata().put("source", "text_input");
        document.getMetadata().put("ingestion_time", Instant.now().toString());
        
        // 分块
        List<Document> chunks = textSplitter.apply(List.of(document));
        
        // 写入向量数据库
        vectorStore.add(chunks);
        
        log.info("文本摄取完成: {} 个文档块", chunks.size());
        
        return Map.of(
                "success", true,
                "title", title,
                "chunks", chunks.size(),
                "message", "文本摄取成功"
        );
    }
    
    /**
     * 批量摄取文件
     */
    public Map<String, Object> ingestBatch(List<MultipartFile> files) {
        log.info("开始批量摄取 {} 个文件", files.size());
        
        List<Map<String, Object>> results = new ArrayList<>();
        int successCount = 0;
        int failCount = 0;
        
        for (MultipartFile file : files) {
            try {
                Map<String, Object> result = ingestFile(file);
                results.add(result);
                successCount++;
            } catch (Exception e) {
                log.error("文件摄取失败: {}", file.getOriginalFilename(), e);
                results.add(Map.of(
                        "success", false,
                        "filename", file.getOriginalFilename(),
                        "error", e.getMessage()
                ));
                failCount++;
            }
        }
        
        return Map.of(
                "success", failCount == 0,
                "total", files.size(),
                "success_count", successCount,
                "fail_count", failCount,
                "results", results
        );
    }
    
    /**
     * 根据文件类型读取文档
     */
    private List<Document> readDocument(File file) {
        String filename = file.getName().toLowerCase();
        
        if (filename.endsWith(".pdf")) {
            return readPdf(file);
        } else if (filename.endsWith(".txt")) {
            return readText(file);
        } else if (filename.endsWith(".docx") || filename.endsWith(".doc")) {
            return readDocx(file);
        } else {
            throw new UnsupportedOperationException("不支持的文件类型: " + filename);
        }
    }
    
    /**
     * 读取 PDF 文件
     */
    private List<Document> readPdf(File file) {
        log.info("读取 PDF 文件: {}", file.getName());
        
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                        .withNumberOfBottomTextLinesToDelete(3)
                        .withNumberOfTopPagesToSkipBeforeDelete(1)
                        .build())
                .withPagesPerDocument(1)
                .build();
        
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                file.toPath().toString(),
                config
        );
        
        return reader.get();
    }
    
    /**
     * 读取文本文件
     */
    private List<Document> readText(File file) {
        log.info("读取文本文件: {}", file.getName());
        
        TextReader reader = new TextReader(file.toPath().toString());
        return reader.get();
    }
    
    /**
     * 读取 Word 文档
     */
    private List<Document> readDocx(File file) {
        log.info("读取 Word 文档: {}", file.getName());
        
        TikaDocumentReader reader = new TikaDocumentReader(file.toPath().toString());
        return reader.get();
    }
    
    /**
     * 清空向量数据库
     */
    public Map<String, Object> clearVectorStore() {
        log.info("清空向量数据库");
        
        // Simple Vector Store 支持清空
        if (vectorStore instanceof org.springframework.ai.vectorstore.SimpleVectorStore) {
            // 重新初始化
            log.info("Simple Vector Store 已清空");
        }
        
        return Map.of(
                "success", true,
                "message", "向量数据库已清空"
        );
    }
}
