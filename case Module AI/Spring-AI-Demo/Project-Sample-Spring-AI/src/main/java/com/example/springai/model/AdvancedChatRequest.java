package com.example.springai.model;

import lombok.Data;

import java.util.List;

/**
 * 高级对话请求 DTO
 * 
 * 提供完整的 AI 对话配置选项，所有字段都是可选的。
 * 
 * @author Spring AI Demo
 */
@Data
public class AdvancedChatRequest {
    
    /** 用户消息内容 */
    private String message;
    
    /** 系统提示词 - 定义 AI 的角色和行为 */
    private String systemPrompt;
    
    /** 用户名称 - 用于个性化对话 */
    private String userName;
    
    /** 对话主题 - 提供上下文 */
    private String topic;
    
    /** 
     * AI 模型名称
     * 常用: gpt-3.5-turbo, gpt-4, deepseek-ai/DeepSeek-V3
     */
    private String model;
    
    /** 
     * 温度参数 (0.0-2.0)
     * 0.0-0.3: 精确任务, 0.7-0.9: 通用对话, 1.0-2.0: 创意任务
     */
    private Double temperature;
    
    /** 
     * 最大 Token 数
     * 100-300: 简短, 500-1000: 中等, 1500-2000: 长文本
     */
    private Integer maxTokens;
    
    /** 
     * 核采样 (0.0-1.0)
     * 控制词汇多样性，推荐 0.9
     */
    private Double topP;
    
    /** 
     * 频率惩罚 (-2.0-2.0)
     * 降低重复词汇，推荐 0.0-0.5
     */
    private Double frequencyPenalty;
    
    /** 
     * 存在惩罚 (-2.0-2.0)
     * 鼓励新话题，推荐 0.0-0.5
     */
    private Double presencePenalty;
    
    /** 对话历史 - 用于多轮对话 */
    private List<String> history;
}
