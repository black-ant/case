package com.example.multillm.controller;

import com.example.multillm.model.ChatRequest;
import com.example.multillm.model.ChatResponse;
import com.example.multillm.model.CustomChannelRequest;
import com.example.multillm.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/llm")
@RequiredArgsConstructor
public class LLMController {
    
    private final OpenAIService openAIService;
    private final AzureOpenAIService azureOpenAIService;
    private final AnthropicService anthropicService;
    private final OllamaService ollamaService;
    private final GeminiService geminiService;
    private final CustomChannelService customChannelService;
    
    /**
     * OpenAI 简单对话
     */
    @PostMapping("/openai/chat")
    public Mono<ChatResponse> openaiChat(@RequestBody ChatRequest request) {
        log.info("=== OpenAI Chat Request ===");
        log.info("Message: {}", request.getMessage());
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = openAIService.chat(request.getMessage());
                long duration = System.currentTimeMillis() - start;
                log.info("OpenAI chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("OpenAI", "gpt-3.5-turbo", response, duration);
            } catch (Exception e) {
                log.error("OpenAI chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * OpenAI 流式对话
     */
    @PostMapping(value = "/openai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> openaiStreamChat(@RequestBody ChatRequest request) {
        log.info("=== OpenAI Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        return openAIService.streamChat(request.getMessage())
                .doOnSubscribe(s -> log.info("OpenAI stream started"))
                .doOnComplete(() -> log.info("OpenAI stream completed"))
                .doOnError(e -> log.error("OpenAI stream error: {}", e.getMessage(), e));
    }
    
    /**
     * Azure OpenAI 简单对话
     */
    @PostMapping("/azure/chat")
    public Mono<ChatResponse> azureChat(@RequestBody ChatRequest request) {
        log.info("=== Azure OpenAI Chat Request ===");
        log.info("Message: {}", request.getMessage());
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = azureOpenAIService.chat(request.getMessage());
                long duration = System.currentTimeMillis() - start;
                log.info("Azure OpenAI chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("Azure OpenAI", "gpt-35-turbo", response, duration);
            } catch (Exception e) {
                log.error("Azure OpenAI chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * Azure OpenAI 流式对话
     */
    @PostMapping(value = "/azure/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> azureStreamChat(@RequestBody ChatRequest request) {
        log.info("=== Azure OpenAI Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        return azureOpenAIService.streamChat(request.getMessage())
                .doOnSubscribe(s -> log.info("Azure OpenAI stream started"))
                .doOnComplete(() -> log.info("Azure OpenAI stream completed"))
                .doOnError(e -> log.error("Azure OpenAI stream error: {}", e.getMessage(), e));
    }
    
    /**
     * Anthropic Claude 简单对话
     */
    @PostMapping("/anthropic/chat")
    public Mono<ChatResponse> anthropicChat(@RequestBody ChatRequest request) {
        log.info("=== Anthropic Claude Chat Request ===");
        log.info("Message: {}", request.getMessage());
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = anthropicService.chat(request.getMessage());
                long duration = System.currentTimeMillis() - start;
                log.info("Anthropic chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("Anthropic", "claude-3-sonnet", response, duration);
            } catch (Exception e) {
                log.error("Anthropic chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * Anthropic Claude 流式对话
     */
    @PostMapping(value = "/anthropic/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> anthropicStreamChat(@RequestBody ChatRequest request) {
        log.info("=== Anthropic Claude Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        return anthropicService.streamChat(request.getMessage())
                .doOnSubscribe(s -> log.info("Anthropic stream started"))
                .doOnComplete(() -> log.info("Anthropic stream completed"))
                .doOnError(e -> log.error("Anthropic stream error: {}", e.getMessage(), e));
    }
    
    /**
     * Ollama 本地模型简单对话
     */
    @PostMapping("/ollama/chat")
    public Mono<ChatResponse> ollamaChat(@RequestBody ChatRequest request) {
        log.info("=== Ollama Chat Request ===");
        log.info("Message: {}", request.getMessage());
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = ollamaService.chat(request.getMessage());
                long duration = System.currentTimeMillis() - start;
                log.info("Ollama chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("Ollama", "llama2", response, duration);
            } catch (Exception e) {
                log.error("Ollama chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * Ollama 本地模型流式对话
     */
    @PostMapping(value = "/ollama/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ollamaStreamChat(@RequestBody ChatRequest request) {
        log.info("=== Ollama Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        return ollamaService.streamChat(request.getMessage())
                .doOnSubscribe(s -> log.info("Ollama stream started"))
                .doOnComplete(() -> log.info("Ollama stream completed"))
                .doOnError(e -> log.error("Ollama stream error: {}", e.getMessage(), e));
    }
    
    /**
     * Google Gemini 简单对话
     */
    @PostMapping("/gemini/chat")
    public Mono<ChatResponse> geminiChat(@RequestBody ChatRequest request) {
        log.info("=== Google Gemini Chat Request ===");
        log.info("Message: {}", request.getMessage());
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = geminiService.chat(request.getMessage());
                long duration = System.currentTimeMillis() - start;
                log.info("Gemini chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("Google Gemini", "gemini-pro", response, duration);
            } catch (Exception e) {
                log.error("Gemini chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * Google Gemini 流式对话
     */
    @PostMapping(value = "/gemini/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> geminiStreamChat(@RequestBody ChatRequest request) {
        log.info("=== Google Gemini Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        return geminiService.streamChat(request.getMessage())
                .doOnSubscribe(s -> log.info("Gemini stream started"))
                .doOnComplete(() -> log.info("Gemini stream completed"))
                .doOnError(e -> log.error("Gemini stream error: {}", e.getMessage(), e));
    }
    
    /**
     * 自定义渠道简单对话
     */
    @PostMapping("/custom/chat")
    public Mono<ChatResponse> customChat(@RequestBody CustomChannelRequest request) {
        log.info("=== Custom Channel Chat Request ===");
        log.info("Message: {}", request.getMessage());
        log.info("BaseURL: {}", request.getBaseUrl());
        log.info("Model: {}", request.getModel() != null ? request.getModel() : "default");
        log.debug("API Token: {}***", request.getApiToken() != null ? request.getApiToken().substring(0, Math.min(10, request.getApiToken().length())) : "null");
        
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            try {
                String response = customChannelService.chat(
                    request.getMessage(), 
                    request.getBaseUrl(), 
                    request.getApiToken(),
                    request.getModel()
                );
                long duration = System.currentTimeMillis() - start;
                String modelName = request.getModel() != null && !request.getModel().isEmpty() 
                    ? request.getModel() : "gpt-3.5-turbo";
                log.info("Custom channel chat completed successfully in {}ms", duration);
                log.debug("Response preview: {}", response.substring(0, Math.min(100, response.length())));
                return new ChatResponse("Custom Channel", modelName, response, duration);
            } catch (Exception e) {
                log.error("Custom channel chat failed: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * 自定义渠道流式对话
     */
    @PostMapping(value = "/custom/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> customStreamChat(@RequestBody CustomChannelRequest request) {
        log.info("=== Custom Channel Stream Chat Request ===");
        log.info("Message: {}", request.getMessage());
        log.info("BaseURL: {}", request.getBaseUrl());
        log.info("Model: {}", request.getModel() != null ? request.getModel() : "default");
        log.debug("API Token: {}***", request.getApiToken() != null ? request.getApiToken().substring(0, Math.min(10, request.getApiToken().length())) : "null");
        
        return customChannelService.streamChat(
            request.getMessage(), 
            request.getBaseUrl(), 
            request.getApiToken(),
            request.getModel()
        )
        .doOnSubscribe(s -> log.info("Custom channel stream started"))
        .doOnComplete(() -> log.info("Custom channel stream completed"))
        .doOnError(e -> log.error("Custom channel stream error: {}", e.getMessage(), e));
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Mono<String> health() {
        log.info("Health check requested");
        return Mono.just("Multi-LLM Provider Service is running!");
    }
}
