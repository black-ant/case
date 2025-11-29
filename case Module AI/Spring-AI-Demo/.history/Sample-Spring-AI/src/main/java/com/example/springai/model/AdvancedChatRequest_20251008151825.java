package com.example.springai.model;

import lombok.Data;

import java.util.List;

/**
 * 高级对话请求 DTO
 * 
 * 提供完整的 AI 对话配置选项，支持自定义系统提示词、模型参数、对话历史等。
 * 所有字段都是可选的，可以根据需要选择性地设置。
 * 
 * 使用示例：
 * <pre>
 * AdvancedChatRequest request = new AdvancedChatRequest();
 * request.setMessage("请解释量子计算");
 * request.setSystemPrompt("你是一个物理学专家");
 * request.setTemperature(0.7);
 * request.setMaxTokens(1000);
 * </pre>
 * 
 * @author Spring AI Demo
 * @see com.example.springai.controller.AdvancedChatController
 */
@Data
public class AdvancedChatRequest {
    
    /**
     * 用户消息内容
     * 
     * 用户想要发送给 AI 的问题或指令。
     * 
     * 示例：
     * - "请解释一下量子计算的原理"
     * - "帮我写一个 Spring Boot 的 REST API"
     * - "翻译这段文字"
     */
    private String message;
    
    /**
     * 系统提示词（System Prompt）
     * 
     * 用于定义 AI 的角色、行为和回答风格。
     * 系统提示词会影响 AI 的整体表现和回答方式。
     * 
     * 示例：
     * - "你是一个专业的 Java 开发专家，擅长 Spring 框架"
     * - "你是一个友好的客服助手，总是用礼貌的语气回答"
     * - "你是一个严谨的科学家，只提供基于事实的答案"
     * 
     * 最佳实践：
     * - 明确定义 AI 的角色和专业领域
     * - 说明期望的回答风格（正式/随意、简洁/详细等）
     * - 提供必要的背景信息和约束条件
     */
    private String systemPrompt;
    
    /**
     * 用户名称
     * 
     * 用于个性化对话，AI 可以在回答中使用用户名称。
     * 
     * 示例：
     * - "张三"
     * - "小明"
     * - "Alice"
     */
    private String userName;
    
    /**
     * 对话主题
     * 
     * 用于指定对话的主题或领域，帮助 AI 更好地理解上下文。
     * 
     * 示例：
     * - "Spring Boot 开发"
     * - "机器学习"
     * - "前端开发"
     */
    private String topic;
    
    /**
     * AI 模型名称
     * 
     * 指定要使用的 AI 模型。不同的模型有不同的能力和成本。
     * 
     * 常用模型：
     * - "gpt-3.5-turbo" - 快速、便宜，适合简单任务
     * - "gpt-4" - 强大、准确，适合复杂任务
     * - "gpt-4-turbo" - GPT-4 的优化版本，更快更便宜
     * - "deepseek-ai/DeepSeek-V3" - DeepSeek 模型
     * - "Qwen/Qwen2.5-7B-Instruct" - 通义千问模型
     * 
     * 如果不指定，将使用配置文件中的默认模型。
     */
    private String model;
    
    /**
     * 温度参数（Temperature）
     * 
     * 控制输出的随机性和创造性。
     * 
     * 取值范围：0.0 - 2.0
     * 
     * 推荐值：
     * - 0.0-0.3：精确、确定性输出（事实问答、代码生成、数学计算）
     * - 0.7-0.9：平衡创造性和准确性（通用对话、内容创作）
     * - 1.0-2.0：高度创造性输出（创意写作、头脑风暴、艺术创作）
     * 
     * 示例：
     * - 0.1：回答 "1+1=?" → "2"（确定性）
     * - 1.5：写故事 → 更有创意和想象力
     * 
     * 注意：温度越高，输出越不可预测，可能产生不准确的内容。
     */
    private Double temperature;
    
    /**
     * 最大 Token 数（Max Tokens）
     * 
     * 限制 AI 生成的最大 token 数量。
     * 1 个 token 约等于 0.75 个英文单词，或 1-2 个中文字符。
     * 
     * 推荐值：
     * - 100-300：简短回答、快速问答
     * - 500-1000：中等长度内容、详细解释
     * - 1500-2000：长文本生成、文章创作
     * - 2000+：长篇内容、详细文档
     * 
     * 作用：
     * - 控制输出长度
     * - 节省 API 成本
     * - 减少响应时间
     * 
     * 注意：如果设置太小，AI 的回答可能被截断。
     */
    private Integer maxTokens;
    
    /**
     * Top P（核采样）
     * 
     * 控制词汇选择的多样性，与 temperature 类似但工作方式不同。
     * 
     * 取值范围：0.0 - 1.0
     * 
     * 工作原理：
     * - 只考虑累积概率达到 topP 的词汇
     * - 例如 topP=0.9 表示只考虑概率最高的 90% 的词汇
     * 
     * 推荐值：
     * - 0.1：非常保守，只选择最可能的词（精确任务）
     * - 0.9：平衡多样性和准确性（通用场景）
     * - 1.0：考虑所有可能的词（最大多样性）
     * 
     * 最佳实践：
     * - 通常与 temperature 配合使用
     * - 一般设置为 0.9 即可
     * - 不建议同时大幅调整 temperature 和 topP
     */
    private Double topP;
    
    /**
     * 频率惩罚（Frequency Penalty）
     * 
     * 降低 AI 重复使用相同词汇的倾向。
     * 
     * 取值范围：-2.0 - 2.0
     * 
     * 效果：
     * - 正值：减少重复词汇，鼓励使用不同的表达
     * - 0：不进行惩罚（默认）
     * - 负值：增加重复词汇（很少使用）
     * 
     * 推荐值：
     * - 0.0：默认，不进行惩罚
     * - 0.3-0.5：轻微减少重复，适合大多数场景
     * - 0.8-1.0：显著减少重复，适合创意写作
     * 
     * 使用场景：
     * - 创意写作：避免重复使用相同的词汇
     * - 长文本生成：保持内容多样性
     * - 对话系统：避免机械式重复
     * 
     * 注意：设置过高可能导致输出不自然。
     */
    private Double frequencyPenalty;
    
    /**
     * 存在惩罚（Presence Penalty）
     * 
     * 鼓励 AI 谈论新话题，而不是重复已经提到的内容。
     * 
     * 取值范围：-2.0 - 2.0
     * 
     * 效果：
     * - 正值：鼓励引入新话题和概念
     * - 0：不进行惩罚（默认）
     * - 负值：鼓励深入讨论已有话题（很少使用）
     * 
     * 推荐值：
     * - 0.0：默认，不进行惩罚
     * - 0.3-0.5：轻微鼓励新话题
     * - 0.8-1.0：显著鼓励新话题，适合头脑风暴
     * 
     * 使用场景：
     * - 头脑风暴：产生更多不同的想法
     * - 创意写作：引入新的情节和元素
     * - 探索性对话：扩展讨论范围
     * 
     * 与 frequencyPenalty 的区别：
     * - frequencyPenalty：关注词汇级别的重复
     * - presencePenalty：关注话题级别的重复
     */
    private Double presencePenalty;
    
    /**
     * 对话历史
     * 
     * 用于多轮对话，提供之前的对话内容作为上下文。
     * AI 可以基于历史对话理解当前问题。
     * 
     * 格式：字符串列表，每个元素代表一轮对话
     * 
     * 示例：
     * <pre>
     * List&lt;String&gt; history = Arrays.asList(
     *     "用户: 什么是 Spring Boot？",
     *     "AI: Spring Boot 是一个用于简化 Spring 应用开发的框架...",
     *     "用户: 它有什么优点？",
     *     "AI: Spring Boot 的主要优点包括..."
     * );
     * </pre>
     * 
     * 使用场景：
     * - 多轮对话：保持对话连贯性
     * - 上下文理解：AI 可以理解代词和引用
     * - 个性化对话：基于历史调整回答风格
     * 
     * 注意事项：
     * - 历史记录会占用 token 配额
     * - 建议只保留最近的几轮对话
     * - 可以定期清理不重要的历史记录
     */
    private List<String> history;
}

/**
 * 参数配置最佳实践
 * 
 * 1. 事实性问答（精确、可靠）
 *    temperature: 0.1-0.3
 *    topP: 0.1-0.3
 *    maxTokens: 300-500
 * 
 * 2. 代码生成（准确、规范）
 *    temperature: 0.2-0.4
 *    topP: 0.9
 *    maxTokens: 1000-2000
 *    frequencyPenalty: 0.0
 * 
 * 3. 通用对话（平衡）
 *    temperature: 0.7-0.9
 *    topP: 0.9
 *    maxTokens: 500-1000
 *    frequencyPenalty: 0.3
 *    presencePenalty: 0.3
 * 
 * 4. 创意写作（创新、多样）
 *    temperature: 1.0-1.5
 *    topP: 1.0
 *    maxTokens: 1500-2000
 *    frequencyPenalty: 0.6
 *    presencePenalty: 0.6
 * 
 * 5. 头脑风暴（发散思维）
 *    temperature: 1.2-2.0
 *    topP: 1.0
 *    maxTokens: 1000-1500
 *    frequencyPenalty: 0.8
 *    presencePenalty: 0.8
 * 
 * 6. 翻译任务（准确、流畅）
 *    temperature: 0.3
 *    topP: 0.9
 *    maxTokens: 根据原文长度
 *    frequencyPenalty: 0.0
 * 
 * 7. 摘要生成（简洁、准确）
 *    temperature: 0.3-0.5
 *    topP: 0.9
 *    maxTokens: 200-500
 *    frequencyPenalty: 0.3
 */
