package com.example.springai.controller;

import com.example.common.audit.ApiAuditLog;
import com.example.springai.model.AdvancedChatRequest;
import com.example.springai.service.AdvancedChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 展示 ChatClient 高级配置的控制器
 */
@RestController
@RequestMapping("/api/advanced")
public class AdvancedChatController {
    
    private final AdvancedChatService advancedChatService;
    
    public AdvancedChatController(AdvancedChatService advancedChatService) {
        this.advancedChatService = advancedChatService;
    }
    
    /**
     * 1. 基础选项配置
     */
    @ApiAuditLog(apiName = "基础选项配置", description = "演示基础 ChatClient 选项配置")
    @PostMapping("/basic-options")
    public String basicOptions(@RequestBody String message) {
        return advancedChatService.chatWithBasicOptions(message);
    }
    
    /**
     * 2. 自定义系统提示词
     */
    @ApiAuditLog(apiName = "自定义系统提示词", description = "使用自定义系统提示词")
    @PostMapping("/system-prompt")
    public String systemPrompt(@RequestBody AdvancedChatRequest request) {
        return advancedChatService.chatWithSystemPrompt(
                request.getMessage(),
                request.getSystemPrompt()
        );
    }
    
    /**
     * 3. 使用模板和变量
     */
    @ApiAuditLog(apiName = "模板和变量", description = "使用模板和变量进行对话")
    @PostMapping("/template")
    public String template(@RequestBody AdvancedChatRequest request) {
        return advancedChatService.chatWithTemplate(
                request.getUserName(),
                request.getTopic()
        );
    }
    
    /**
     * 4. 高级选项配置
     */
    @ApiAuditLog(apiName = "高级选项配置", description = "演示高级 ChatClient 选项配置")
    @PostMapping("/advanced-options")
    public String advancedOptions(@RequestBody String message) {
        return advancedChatService.chatWithAdvancedOptions(message);
    }
    
    /**
     * 5. 流式响应配置
     */
    @ApiAuditLog(apiName = "流式响应配置", description = "演示流式响应配置")
    @PostMapping(value = "/stream-options", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamOptions(@RequestBody String message) {
        return advancedChatService.streamWithOptions(message);
    }
    
    /**
     * 6. 使用 Prompt 对象
     */
    @ApiAuditLog(apiName = "Prompt 对象", description = "使用 Prompt 对象进行对话")
    @PostMapping("/prompt-object")
    public String promptObject(@RequestBody AdvancedChatRequest request) {
        return advancedChatService.chatWithPromptObject(
                request.getSystemPrompt(),
                request.getMessage()
        );
    }
    
    /**
     * 7. 获取完整响应信息
     */
    @ApiAuditLog(apiName = "完整响应信息", description = "获取完整的响应信息")
    @PostMapping("/full-response")
    public Map<String, Object> fullResponse(@RequestBody String message) {
        return advancedChatService.chatWithFullResponse(message);
    }
    
    /**
     * 8. 指定模型
     */
    @ApiAuditLog(apiName = "指定模型", description = "使用指定的 AI 模型")
    @PostMapping("/specific-model")
    public String specificModel(@RequestBody AdvancedChatRequest request) {
        return advancedChatService.chatWithSpecificModel(
                request.getMessage(),
                request.getModel()
        );
    }
    
    /**
     * 9. 创造性写作
     */
    @ApiAuditLog(apiName = "创造性写作", description = "高温度参数的创造性写作")
    @PostMapping("/creative-writing")
    public String creativeWriting(@RequestBody AdvancedChatRequest request) {
        return advancedChatService.creativeWriting(request.getTopic());
    }
    
    /**
     * 10. 精确回答
     */
    @ApiAuditLog(apiName = "精确回答", description = "低温度参数的精确回答")
    @PostMapping("/precise-answer")
    public String preciseAnswer(@RequestBody String question) {
        return advancedChatService.preciseAnswer(question);
    }
}
