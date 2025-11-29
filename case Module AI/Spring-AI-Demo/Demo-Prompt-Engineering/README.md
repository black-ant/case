# Prompt Engineering Demo - 提示工程示例

这个项目展示了如何使用 Spring AI 进行提示工程（Prompt Engineering），通过精心设计的提示词来引导 AI 生成更准确、更符合需求的回复。

## 🎯 功能特性

- ✅ 系统提示词设计
- ✅ 提示词模板
- ✅ 角色扮演
- ✅ Few-Shot Learning
- ✅ Chain of Thought
- ✅ 输出格式控制

## 🏗️ 核心概念

### 提示工程的重要性

好的提示词可以：
- 提高回答准确性
- 控制输出格式
- 引导特定风格
- 减少幻觉
- 提升用户体验

## 📦 依赖项

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

## 🚀 快速开始

### 1. 启动应用

```bash
cd Prompt-Engineering-Demo
mvn spring-boot:run
```

### 2. 测试不同的提示词

```bash
curl -X POST http://localhost:8084/api/prompt/role \
  -H "Content-Type: application/json" \
  -d '{"message": "如何学习编程？"}'
```

## 💡 核心示例

### 示例 1: 基础提示词

```java
@Service
public class BasicPromptService {
    
    private final ChatClient chatClient;
    
    public String simplePrompt(String userMessage) {
        return chatClient.prompt()
            .user(userMessage)
            .call()
            .content();
    }
}
```

### 示例 2: 系统提示词

```java
public String withSystemPrompt(String userMessage) {
    String systemPrompt = """
        你是一位专业的编程导师。
        你的回答应该：
        1. 清晰易懂
        2. 包含实际例子
        3. 循序渐进
        4. 鼓励学习者
        """;
    
    return chatClient.prompt()
        .system(systemPrompt)
        .user(userMessage)
        .call()
        .content();
}
```

### 示例 3: 角色扮演

```java
public String rolePlay(String role, String userMessage) {
    String systemPrompt = String.format(
        "你现在扮演一位%s。请以这个角色的身份和专业知识来回答问题。",
        role
    );
    
    return chatClient.prompt()
        .system(systemPrompt)
        .user(userMessage)
        .call()
        .content();
}
```

### 示例 4: Few-Shot Learning

```java
public String fewShot(String userMessage) {
    String prompt = """
        以下是一些示例：
        
        问题：什么是变量？
        回答：变量就像一个盒子，可以存放数据。比如 int age = 25; 就是创建了一个名为 age 的盒子，里面放着数字 25。
        
        问题：什么是函数？
        回答：函数就像一个小机器，你给它输入，它给你输出。比如计算器的加法功能，输入两个数字，输出它们的和。
        
        现在请用同样的风格回答：
        问题：%s
        回答：
        """.formatted(userMessage);
    
    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
}
```

### 示例 5: Chain of Thought

```java
public String chainOfThought(String problem) {
    String prompt = """
        请一步步思考并解决以下问题：
        
        问题：%s
        
        请按以下格式回答：
        1. 理解问题：[说明你对问题的理解]
        2. 分析步骤：[列出解决步骤]
        3. 执行计算：[展示计算过程]
        4. 得出结论：[给出最终答案]
        """.formatted(problem);
    
    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
}
```

### 示例 6: 输出格式控制

```java
public String structuredOutput(String topic) {
    String prompt = """
        请以 JSON 格式输出关于"%s"的信息：
        {
          "title": "标题",
          "summary": "简短摘要",
          "keyPoints": ["要点1", "要点2", "要点3"],
          "difficulty": "难度等级（初级/中级/高级）"
        }
        """.formatted(topic);
    
    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
}
```

## 🎓 最佳实践

### 1. 清晰的指令

❌ **不好的提示词**
```
告诉我关于 Java 的事情
```

✅ **好的提示词**
```
请用 3-5 句话简要介绍 Java 编程语言的主要特点和应用场景。
```

### 2. 提供上下文

```java
String prompt = """
    背景：我是一名初学者，刚开始学习编程。
    目标：我想了解如何开始学习 Python。
    要求：请给出具体的学习路径和资源推荐。
    
    问题：%s
    """.formatted(userMessage);
```

### 3. 设定角色

```java
String systemPrompt = """
    你是一位有 20 年经验的资深软件工程师。
    你擅长用简单的比喻解释复杂的技术概念。
    你的回答总是包含实际的代码示例。
    """;
```

### 4. 限制输出

```java
String prompt = """
    请用不超过 100 字回答以下问题：
    %s
    
    要求：
    - 简洁明了
    - 突出重点
    - 避免专业术语
    """.formatted(question);
```

### 5. 使用模板

```java
@Component
public class PromptTemplates {
    
    public static final String TEACHER_TEMPLATE = """
        你是一位{subject}老师。
        学生的水平是{level}。
        请用{style}的方式回答问题。
        
        问题：{question}
        """;
    
    public String fillTemplate(Map<String, String> params) {
        String prompt = TEACHER_TEMPLATE;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            prompt = prompt.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return prompt;
    }
}
```

## 📊 提示词模式

### 1. 指令模式

```
请[动作][对象]，要求[条件]。
```

### 2. 对话模式

```
用户：[问题]
助手：[回答]
用户：[追问]
助手：
```

### 3. 填空模式

```
[上下文]
因此，答案是___。
```

### 4. 分类模式

```
将以下内容分类为[类别1]、[类别2]或[类别3]：
[内容]
```

## 🔧 配置说明

### application.yml

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7  # 创造性：0-2
          max-tokens: 1000  # 最大输出长度
          top-p: 1.0        # 采样范围
```

### 参数说明

- **temperature**: 控制随机性（0=确定性，2=创造性）
- **max-tokens**: 限制输出长度
- **top-p**: 核采样参数
- **frequency_penalty**: 降低重复
- **presence_penalty**: 鼓励新话题

## 📊 适用场景

- 📝 **内容生成** - 文章、代码、创意
- 🎓 **教育培训** - 个性化教学
- 💼 **商务应用** - 邮件、报告
- 🤖 **客服系统** - 专业回答
- 🔍 **数据分析** - 结构化输出

## 🔍 进阶技巧

### 1. 多轮对话

```java
public String multiTurn(List<Message> history, String newMessage) {
    return chatClient.prompt()
        .messages(history)
        .user(newMessage)
        .call()
        .content();
}
```

### 2. 动态提示词

```java
public String dynamicPrompt(UserProfile profile, String question) {
    String systemPrompt = String.format("""
        用户信息：
        - 经验水平：%s
        - 偏好风格：%s
        - 学习目标：%s
        
        请根据用户特点调整回答方式。
        """,
        profile.getLevel(),
        profile.getStyle(),
        profile.getGoal()
    );
    
    return chatClient.prompt()
        .system(systemPrompt)
        .user(question)
        .call()
        .content();
}
```

### 3. 提示词链

```java
public String promptChain(String input) {
    // 第一步：分析
    String analysis = chatClient.prompt()
        .user("分析以下内容的主题：" + input)
        .call()
        .content();
    
    // 第二步：扩展
    String expansion = chatClient.prompt()
        .user("基于主题 '" + analysis + "' 生成详细内容")
        .call()
        .content();
    
    return expansion;
}
```

## 🐛 常见问题

**Q: 如何避免 AI 幻觉？**
A: 使用明确的指令、提供上下文、要求引用来源。

**Q: 如何控制输出长度？**
A: 在提示词中明确要求字数，或使用 max-tokens 参数。

**Q: 如何让回答更专业？**
A: 设定专业角色、提供领域知识、使用专业术语。

**Q: 如何提高一致性？**
A: 降低 temperature 参数、使用固定的提示词模板。

## 📚 相关资源

- [OpenAI Prompt Engineering Guide](https://platform.openai.com/docs/guides/prompt-engineering)
- [Prompt Engineering Best Practices](https://www.promptingguide.ai/)
- [Spring AI Prompts 文档](https://docs.spring.io/spring-ai/reference/api/prompts.html)

## 🔗 相关项目

- [AI-Customer-Service](../AI-Customer-Service/) - 应用了提示工程的完整系统
- [Agent-Workflow-Demo](../Agent-Workflow-Demo/) - 复杂提示词应用

---

[返回主页](../README.md)
