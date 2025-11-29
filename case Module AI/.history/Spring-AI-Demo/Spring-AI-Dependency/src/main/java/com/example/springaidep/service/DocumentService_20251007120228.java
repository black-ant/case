package com.example.springaidep.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档处理服务
 * 处理各种格式的文档加载和分割
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    /**
     * 加载 PDF 文档
     */
    public List<Document> loadPdf(MultipartFile file) {
        try {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes());
            PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
            List<Document> documents = reader.get();
            
            log.info("PDF 加载完成: {} 页", documents.size());
            return documents;
        } catch (IOException e) {
            log.error("加载 PDF 失败", e);
            throw new RuntimeException("Failed to load PDF", e);
        }
    }

    /**
     * 加载 Word 文档
     */
    public List<Document> loadDocx(MultipartFile file) {
        try {
            // 注意: 需要根据实际的 Word 文档读取器实现
            String content = new String(file.getBytes());
            Document document = new Document(content);
            
            log.info("Word 文档加载完成");
            return List.of(document);
        } catch (IOException e) {
            log.error("加载 Word 文档失败", e);
            throw new RuntimeException("Failed to load Word document", e);
        }
    }

    /**
     * 加载 Markdown 文档
     */
    public List<Document> loadMarkdown(MultipartFile file) {
        try {
            String content = new String(file.getBytes());
            Document document = new Document(content);
            
            log.info("Markdown 文档加载完成");
            return List.of(document);
        } catch (IOException e) {
            log.error("加载 Markdown 文档失败", e);
            throw new RuntimeException("Failed to load Markdown document", e);
        }
    }

    /**
     * 从 URL 加载文档
     */
    public List<Document> loadFromUrl(String url) {
        // 注意: 需要根据实际的 Web 文档读取器实现
        log.info("从 URL 加载文档: {}", url);
        
        // 示例实现
        Document document = new Document("Content from " + url);
        return List.of(document);
    }

    /**
     * 加载并分割文档
     */
    public List<Document> loadAndSplit(MultipartFile file, int chunkSize, int chunkOverlap) {
        try {
            // 先加载文档
            List<Document> documents = loadPdf(file);
            
            // 使用 TokenTextSplitter 分割
            TokenTextSplitter splitter = new TokenTextSplitter(chunkSize, chunkOverlap);
            List<Document> splitDocuments = new ArrayList<>();
            
            for (Document doc : documents) {
                List<Document> chunks = splitter.split(doc);
                splitDocuments.addAll(chunks);
            }
            
            log.info("文档分割完成: {} 个原始文档 -> {} 个块", documents.size(), splitDocuments.size());
            return splitDocuments;
            
        } catch (Exception e) {
            log.error("文档分割失败", e);
            throw new RuntimeException("Failed to split document", e);
        }
    }

    /**
     * 提取文档元数据
     */
    public void extractMetadata(Document document) {
        // 可以添加自定义元数据提取逻辑
        document.getMetadata().put("processed", true);
        document.getMetadata().put("timestamp", System.currentTimeMillis());
    }
}
