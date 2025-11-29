package com.example.rag.controller;

import com.example.common.audit.ApiAuditLog;
import com.example.rag.service.DocumentIngestionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文档管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@ApiAuditLog
public class DocumentController {
    
    private final DocumentIngestionService documentIngestionService;
    
    @Data
    public static class TextRequest {
        private String text;
        private String title;
    }
    
    /**
     * 1. 上传单个文件
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return documentIngestionService.ingestFile(file);
    }
    
    /**
     * 2. 批量上传文件
     */
    @PostMapping("/upload/batch")
    public Map<String, Object> uploadBatch(@RequestParam("files") List<MultipartFile> files) {
        return documentIngestionService.ingestBatch(files);
    }
    
    /**
     * 3. 摄取文本内容
     */
    @PostMapping("/ingest/text")
    public Map<String, Object> ingestText(@RequestBody TextRequest request) {
        return documentIngestionService.ingestText(request.getText(), request.getTitle());
    }
    
    /**
     * 4. 清空向量数据库
     */
    @DeleteMapping("/clear")
    public Map<String, Object> clearVectorStore() {
        return documentIngestionService.clearVectorStore();
    }
}
