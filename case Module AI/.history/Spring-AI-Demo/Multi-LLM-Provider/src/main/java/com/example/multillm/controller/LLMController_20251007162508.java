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
        return openAIService.streamChat(request.getMessage());
    }
    
    /**
     * Azure OpenAI 简单对话
     */
    @PostMapping("/azure/chat")
    public Mono<ChatResponse> azureChat(@RequestBody ChatRequest request) {
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            String response = azureOpenAIService.chat(request.getMessage());
            long duration = System.currentTimeMillis() - start;
            return new ChatResponse("Azure OpenAI", "gpt-35-turbo", response, duration);
        });
    }
    
    /**
     * Azure OpenAI 流式对话
     */
    @PostMapping(value = "/azure/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> azureStreamChat(@RequestBody ChatRequest request) {
        return azureOpenAIService.streamChat(request.getMessage());
    }
    
    /**
     * Anthropic Claude 简单对话
     */
    @PostMapping("/anthropic/chat")
    public Mono<ChatResponse> anthropicChat(@RequestBody ChatRequest request) {
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            String response = anthropicService.chat(request.getMessage());
            long duration = System.currentTimeMillis() - start;
            return new ChatResponse("Anthropic", "claude-3-sonnet", response, duration);
        });
    }
    
    /**
     * Anthropic Claude 流式对话
     */
    @PostMapping(value = "/anthropic/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> anthropicStreamChat(@RequestBody ChatRequest request) {
        return anthropicService.streamChat(request.getMessage());
    }
    
    /**
     * Ollama 本地模型简单对话
     */
    @PostMapping("/ollama/chat")
    public Mono<ChatResponse> ollamaChat(@RequestBody ChatRequest request) {
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            String response = ollamaService.chat(request.getMessage());
            long duration = System.currentTimeMillis() - start;
            return new ChatResponse("Ollama", "llama2", response, duration);
        });
    }
    
    /**
     * Ollama 本地模型流式对话
     */
    @PostMapping(value = "/ollama/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ollamaStreamChat(@RequestBody ChatRequest request) {
        return ollamaService.streamChat(request.getMessage());
    }
    
    /**
     * Google Gemini 简单对话
     */
    @PostMapping("/gemini/chat")
    public Mono<ChatResponse> geminiChat(@RequestBody ChatRequest request) {
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            String response = geminiService.chat(request.getMessage());
            long duration = System.currentTimeMillis() - start;
            return new ChatResponse("Google Gemini", "gemini-pro", response, duration);
        });
    }
    
    /**
     * Google Gemini 流式对话
     */
    @PostMapping(value = "/gemini/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> geminiStreamChat(@RequestBody ChatRequest request) {
        return geminiService.streamChat(request.getMessage());
    }
    
    /**
     * 自定义渠道简单对话
     */
    @PostMapping("/custom/chat")
    public Mono<ChatResponse> customChat(@RequestBody CustomChannelRequest request) {
        long start = System.currentTimeMillis();
        return Mono.fromCallable(() -> {
            String response = customChannelService.chat(
                request.getMessage(), 
                request.getBaseUrl(), 
                request.getApiToken(),
                request.getModel()
            );
            long duration = System.currentTimeMillis() - start;
            String modelName = request.getModel() != null && !request.getModel().isEmpty() 
                ? request.getModel() : "gpt-3.5-turbo";
            return new ChatResponse("Custom Channel", modelName, response, duration);
        });
    }
    
    /**
     * 自定义渠道流式对话
     */
    @PostMapping(value = "/custom/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> customStreamChat(@RequestBody CustomChannelRequest request) {
        return customChannelService.streamChat(
            request.getMessage(), 
            request.getBaseUrl(), 
            request.getApiToken(),
            request.getModel()
        );
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("Multi-LLM Provider Service is running!");
    }
}
