package com.example.springaidep.controller;

import com.example.springaidep.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文档加载控制器
 * 展示不同格式文档的加载
 */
@Slf4j
@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentLoaderController {

    private final DocumentService documentService;

    /**
     * 加载 PDF 文档
     */
    @PostMapping("/load-pdf")
    public Map<String, Object> loadPdf(@RequestParam("file") MultipartFile file) {
        log.info("加载 PDF 文档: {}", file.getOriginalFilename());
        
        List<Document> documents = documentService.loadPdf(file);
        
        return Map.of(
            "status", "success",
            "filename", file.getOriginalFilename(),
            "documentCount", documents.size(),
            "documents", documents.stream()
                .map(doc -> Map.of(
                    "id", doc.getId(),
                    "contentLength", doc.getContent().length(),
                    "metadata", doc.getMetadata()
                ))
                .toList()
        );
    }

    /**
     * 加载 Word 文档
     */
    @PostMapping("/load-docx")
    public Map<String, Object> loadDocx(@RequestParam("file") MultipartFile file) {
        log.info("加载 Word 文档: {}", file.getOriginalFilename());
        
        List<Document> documents = documentService.loadDocx(file);
        
        return Map.of(
            "status", "success",
            "filename", file.getOriginalFilename(),
            "documentCount", documents.size()
        );
    }

    /**
     * 加载 Markdown 文档
     */
    @PostMapping("/load-markdown")
    public Map<String, Object> loadMarkdown(@RequestParam("file") MultipartFile file) {
        log.info("加载 Markdown 文档: {}", file.getOriginalFilename());
        
        List<Document> documents = documentService.loadMarkdown(file);
        
        return Map.of(
            "status", "success",
            "filename", file.getOriginalFilename(),
            "documentCount", documents.size()
        );
    }

    /**
     * 从 URL 加载文档
     */
    @PostMapping("/load-url")
    public Map<String, Object> loadFromUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        log.info("从 URL 加载文档: {}", url);
        
        List<Document> documents = documentService.loadFromUrl(url);
        
        return Map.of(
            "status", "success",
            "url", url,
            "documentCount", documents.size(),
            "totalLength", documents.stream()
                .mapToInt(doc -> doc.getContent().length())
                .sum()
        );
    }

    /**
     * 加载并分割文档
     */
    @PostMapping("/load-and-split")
    public Map<String, Object> loadAndSplit(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "1000") int chunkSize,
            @RequestParam(defaultValue = "200") int chunkOverlap) {
        
        log.info("加载并分割文档: {}, chunkSize={}, overlap={}", 
                file.getOriginalFilename(), chunkSize, chunkOverlap);
        
        List<Document> documents = documentService.loadAndSplit(file, chunkSize, chunkOverlap);
        
        return Map.of(
            "status", "success",
            "filename", file.getOriginalFilename(),
            "chunkCount", documents.size(),
            "chunkSize", chunkSize,
            "chunkOverlap", chunkOverlap,
            "chunks", documents.stream()
                .limit(5) // 只返回前5个块作为示例
                .map(doc -> Map.of(
                    "id", doc.getId(),
                    "contentPreview", doc.getContent().substring(0, Math.min(100, doc.getContent().length()))
                ))
                .toList()
        );
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "service", "DocumentLoader",
            "message", "Document loader service is running"
        );
    }
}
