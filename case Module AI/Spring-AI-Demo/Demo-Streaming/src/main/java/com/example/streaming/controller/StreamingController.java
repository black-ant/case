package com.example.streaming.controller;

import com.example.streaming.service.StreamingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * 流式响应控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
public class StreamingController {
    
    private final StreamingService streamingService;
    
    @Data
    public static class StreamRequest {
        private String message;
        private String systemPrompt;
        private String context;
        private Double temperature;
        private Integer maxTokens;
        private Long delayMillis;
        private String prefix;
        private String[] messages;
    }
    
    /**
     * 1. 基础流式响应 - SSE
     */
    @PostMapping(value = "/basic", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> basicStream(@RequestBody StreamRequest request) {
        return streamingService.basicStream(request.getMessage());
    }
    
    /**
     * 2. 基础流式响应 - NDJSON
     */
    @PostMapping(value = "/basic/ndjson", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> basicStreamNdjson(@RequestBody StreamRequest request) {
        return streamingService.basicStream(request.getMessage());
    }
    
    /**
     * 3. 带延迟的流式响应（打字机效果）
     */
    @PostMapping(value = "/delayed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> delayedStream(@RequestBody StreamRequest request) {
        long delay = request.getDelayMillis() != null ? request.getDelayMillis() : 50;
        return streamingService.delayedStream(request.getMessage(), delay);
    }
    
    /**
     * 4. 带系统提示词的流式响应
     */
    @PostMapping(value = "/with-system", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWithSystem(@RequestBody StreamRequest request) {
        return streamingService.streamWithSystem(
                request.getSystemPrompt(),
                request.getMessage()
        );
    }
    
    /**
     * 5. 多轮对话流式响应
     */
    @PostMapping(value = "/conversation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> conversationStream(@RequestBody StreamRequest request) {
        return streamingService.conversationStream(
                request.getContext(),
                request.getMessage()
        );
    }
    
    /**
     * 6. 带选项的流式响应
     */
    @PostMapping(value = "/with-options", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWithOptions(@RequestBody StreamRequest request) {
        Double temperature = request.getTemperature() != null ? request.getTemperature() : 0.7;
        Integer maxTokens = request.getMaxTokens() != null ? request.getMaxTokens() : 1000;
        
        return streamingService.streamWithOptions(
                request.getMessage(),
                temperature,
                maxTokens
        );
    }
    
    /**
     * 7. 流式响应转换 - 添加前缀
     */
    @PostMapping(value = "/with-prefix", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWithPrefix(@RequestBody StreamRequest request) {
        String prefix = request.getPrefix() != null ? request.getPrefix() : "[AI] ";
        return streamingService.streamWithPrefix(request.getMessage(), prefix);
    }
    
    /**
     * 8. 流式响应过滤
     */
    @PostMapping(value = "/filtered", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFiltered(@RequestBody StreamRequest request) {
        return streamingService.streamFiltered(request.getMessage());
    }
    
    /**
     * 9. 流式响应计数
     */
    @PostMapping(value = "/with-count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWithCount(@RequestBody StreamRequest request) {
        return streamingService.streamWithCount(request.getMessage());
    }
    
    /**
     * 10. 并行流式响应
     */
    @PostMapping(value = "/parallel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> parallelStream(@RequestBody StreamRequest request) {
        return streamingService.parallelStream(request.getMessages());
    }
    
    /**
     * 11. 流式响应缓冲
     */
    @PostMapping(value = "/buffered", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamBuffered(@RequestBody StreamRequest request) {
        return streamingService.streamBuffered(request.getMessage());
    }
    
    /**
     * 12. 流式响应错误处理
     */
    @PostMapping(value = "/with-error-handling", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamWithErrorHandling(@RequestBody StreamRequest request) {
        return streamingService.streamWithErrorHandling(request.getMessage());
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "Streaming Demo is running!";
    }
}
