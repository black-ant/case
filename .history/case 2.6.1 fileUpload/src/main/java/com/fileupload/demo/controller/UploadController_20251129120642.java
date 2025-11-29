package com.fileupload.demo.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 * <p>
 * 演示多种文件上传方式：
 * <ul>
 *     <li>使用 MultipartFile 接收</li>
 *     <li>使用 transferTo 保存</li>
 *     <li>使用 Commons IO 保存</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 1.0
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${file.upload.path:./uploads}")
    private String uploadPath;

    /**
     * 基础文件上传
     * <p>
     * POST /upload/upload
     * Content-Type: multipart/form-data
     * </p>
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择文件");
            return result;
        }

        try {
            // 确保上传目录存在
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : "";
            String newFilename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = uploadDir.resolve(newFilename);
            Files.write(filePath, file.getBytes());

            logger.info("File uploaded: {} -> {}", originalFilename, filePath);

            result.put("success", true);
            result.put("message", "上传成功");
            result.put("originalName", originalFilename);
            result.put("savedName", newFilename);
            result.put("size", file.getSize());
            
        } catch (IOException e) {
            logger.error("Upload failed", e);
            result.put("success", false);
            result.put("message", "上传失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 使用 transferTo 方式上传
     * <p>
     * POST /upload/transferTo
     * </p>
     * <p>
     * transferTo 是 MultipartFile 提供的便捷方法，
     * 内部会自动处理临时文件的移动或复制。
     * </p>
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @PostMapping("/transferTo")
    public Map<String, Object> uploadWithTransferTo(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        long startTime = System.currentTimeMillis();

        try {
            String originalFilename = file.getOriginalFilename();
            String newFilename = System.currentTimeMillis() + "_" + originalFilename;
            
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            File destFile = uploadDir.resolve(newFilename).toFile();
            file.transferTo(destFile);

            long endTime = System.currentTimeMillis();
            logger.info("Upload completed in {} ms", endTime - startTime);

            result.put("success", true);
            result.put("filename", newFilename);
            result.put("duration", (endTime - startTime) + "ms");
            
        } catch (IOException e) {
            logger.error("Upload failed", e);
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    /**
     * 多文件上传
     * <p>
     * POST /upload/multiple
     * </p>
     *
     * @param files 多个文件
     * @return 上传结果
     */
    @PostMapping("/multiple")
    public Map<String, Object> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int failCount = 0;

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    Path uploadDir = Paths.get(uploadPath);
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    
                    String newFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Files.write(uploadDir.resolve(newFilename), file.getBytes());
                    successCount++;
                    
                } catch (IOException e) {
                    logger.error("Failed to upload: {}", file.getOriginalFilename(), e);
                    failCount++;
                }
            }
        }

        result.put("success", failCount == 0);
        result.put("total", files.length);
        result.put("successCount", successCount);
        result.put("failCount", failCount);

        return result;
    }
}
