package com.example.agent.workflow;

import com.example.agent.model.MarketAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 并行工作流
 * 同时执行多个独立的任务，提高效率
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParallelWorkflow {
    
    private final ChatClient.Builder chatClientBuilder;
    
    /**
     * 通用并行分析
     */
    public Map<String, String> parallelAnalysis(String input, List<String> perspectives) {
        log.info("开始并行分析，共 {} 个视角", perspectives.size());
        long startTime = System.currentTimeMillis();
        
        List<CompletableFuture<Map.Entry<String, String>>> futures = perspectives.stream()
                .map(perspective -> analyzeFromPerspective(input, perspective))
                .toList();
        
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        Map<String, String> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        long processingTime = System.currentTimeMillis() - startTime;
        log.info("并行分析完成，耗时: {}ms", processingTime);
        
        return results;
    }
    
    /**
     * 异步分析单个视角
     */
    @Async
    public CompletableFuture<Map.Entry<String, String>> analyzeFromPerspective(String input, String perspective) {
        log.info("开始分析视角: {}", perspective);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        String result = chatClient.prompt()
                .system("从 " + perspective + " 的角度进行分析")
                .user(input)
                .call()
                .content();
        
        log.info("视角 {} 分析完成", perspective);
        
        return CompletableFuture.completedFuture(Map.entry(perspective, result));
    }
    
    /**
     * 多角度市场分析
     */
    public MarketAnalysis analyzeMarket(String marketData) {
        log.info("开始多角度市场分析");
        long startTime = System.currentTimeMillis();
        
        List<String> perspectives = List.of(
                "技术分析（Technical Analysis）",
                "基本面分析（Fundamental Analysis）",
                "情绪分析（Sentiment Analysis）",
                "风险评估（Risk Assessment）"
        );
        
        // 并行分析
        Map<String, String> analyses = parallelAnalysis(marketData, perspectives);
        
        // 综合分析结果
        ChatClient chatClient = chatClientBuilder.build();
        
        String consolidatedAnalysis = chatClient.prompt()
                .system("你是一个资深的市场分析师。综合多个角度的分析，给出全面的市场评估。")
                .user(analyses.entrySet().stream()
                        .map(e -> e.getKey() + ":\n" + e.getValue())
                        .collect(Collectors.joining("\n\n")))
                .call()
                .content();
        
        // 生成投资建议
        String recommendation = chatClient.prompt()
                .system("你是一个投资顾问。基于综合分析，给出明确的投资建议。")
                .user("综合分析：\n" + consolidatedAnalysis)
                .call()
                .content();
        
        long processingTime = System.currentTimeMillis() - startTime;
        log.info("市场分析完成，耗时: {}ms", processingTime);
        
        return new MarketAnalysis(analyses, consolidatedAnalysis, recommendation, processingTime);
    }
    
    /**
     * 多语言翻译
     */
    public Map<String, String> translateToMultipleLanguages(String text, List<String> languages) {
        log.info("开始多语言翻译: {}", languages);
        
        return parallelAnalysis(text, languages.stream()
                .map(lang -> "翻译成" + lang)
                .toList());
    }
    
    /**
     * 多角度产品评估
     */
    public Map<String, String> evaluateProduct(String productDescription) {
        log.info("开始多角度产品评估");
        
        List<String> perspectives = List.of(
                "用户体验（User Experience）",
                "技术实现（Technical Implementation）",
                "市场竞争力（Market Competitiveness）",
                "商业价值（Business Value）",
                "创新性（Innovation）"
        );
        
        return parallelAnalysis(productDescription, perspectives);
    }
}
