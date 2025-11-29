# Streaming Demo - 流式响应示例

这个项目展示了如何使用 Spring AI 实现流式响应，通过 SSE (Server-Sent Events) 实时输出 AI 的回复内容，提升用户体验。

## 🎯 功能特性

- ✅ SSE 流式输出
- ✅ WebFlux 响应式编程
- ✅ 实时内容展示
- ✅ 降低首字延迟
- ✅ 支持取消和错误处理

## 🏗️ 技术架构

### 核心技术

- **Spring WebFlux** - 响应式 Web 框架
- **Reactor** - 响应式编程库
- **SSE** - Server-Sent Events 协议
- **Flux** - 响应式流

## 📦 依赖项

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

## 🚀 快速开始

### 1. 启动应用

```bash
cd Streaming-Demo
mvn spring-boot:run
```

### 2. 测试流式响应

```bash
curl -N http://localhost:8083/api/stream/chat?message=讲个故事
```

### 3. 访问 Web 界面

打开浏览器访问: http://localhost:8083

## 💡 核心实现

### 后端实现

```java
@RestController
@RequestMapping("/api/stream")
public class StreamingController {
    
    private final ChatClient chatClient;
    
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam String message) {
        return chatClient.prompt()
            .user(message)
            .stream()
            .content();
    }
}
```

### 前端实现

```javascript
const eventSource = new EventSource('/api/stream/chat?message=' + encodeURIComponent(message));

eventSource.onmessage = (event) => {
    // 逐字显示内容
    displayContent += event.data;
    updateUI(displayContent);
};

eventSource.onerror = () => {
    eventSource.close();
};
```

## 📝 使用示例

### 示例 1: 基础流式响应

```java
@GetMapping(value = "/simple", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> simpleStream(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content();
}
```

### 示例 2: 带元数据的流式响应

```java
@GetMapping(value = "/detailed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<ChatResponse> detailedStream(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .chatResponse();
}
```

### 示例 3: 自定义流式处理

```java
@GetMapping(value = "/custom", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<ServerSentEvent<String>> customStream(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .map(content -> ServerSentEvent.<String>builder()
            .data(content)
            .event("message")
            .build());
}
```

### 示例 4: 错误处理

```java
@GetMapping(value = "/safe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> safeStream(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .onErrorResume(error -> {
            log.error("流式响应错误", error);
            return Flux.just("[错误: " + error.getMessage() + "]");
        });
}
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
          temperature: 0.7
          stream: true  # 启用流式响应

server:
  port: 8083
```

## 🎓 最佳实践

### 1. 性能优化

**使用背压控制**
```java
public Flux<String> streamWithBackpressure(String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .onBackpressureBuffer(100);
}
```

**设置超时**
```java
public Flux<String> streamWithTimeout(String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .timeout(Duration.ofSeconds(30));
}
```

### 2. 错误处理

```java
public Flux<String> streamWithErrorHandling(String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .doOnError(error -> log.error("流式错误", error))
        .onErrorResume(error -> Flux.just("抱歉，发生了错误"))
        .doFinally(signal -> log.info("流式完成: {}", signal));
}
```

### 3. 取消处理

```java
public Flux<String> streamWithCancellation(String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .doOnCancel(() -> log.info("用户取消了请求"));
}
```

### 4. 进度反馈

```java
public Flux<StreamEvent> streamWithProgress(String message) {
    AtomicInteger counter = new AtomicInteger(0);
    
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .map(content -> new StreamEvent(
            content,
            counter.incrementAndGet(),
            System.currentTimeMillis()
        ));
}
```

## 📊 性能对比

| 模式 | 首字延迟 | 总响应时间 | 用户体验 |
|------|---------|-----------|---------|
| 非流式 | 3-5秒 | 3-5秒 | ⭐⭐ |
| 流式 | <1秒 | 3-5秒 | ⭐⭐⭐⭐⭐ |

## 🎨 前端集成

### 使用 EventSource

```javascript
function streamChat(message) {
    const eventSource = new EventSource(
        `/api/stream/chat?message=${encodeURIComponent(message)}`
    );
    
    let fullResponse = '';
    
    eventSource.onmessage = (event) => {
        fullResponse += event.data;
        document.getElementById('response').textContent = fullResponse;
    };
    
    eventSource.onerror = () => {
        eventSource.close();
        console.log('Stream ended');
    };
    
    return eventSource;
}
```

### 使用 Fetch API

```javascript
async function streamChatWithFetch(message) {
    const response = await fetch('/api/stream/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message })
    });
    
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    
    while (true) {
        const { done, value } = await reader.read();
        if (done) break;
        
        const chunk = decoder.decode(value);
        displayChunk(chunk);
    }
}
```

## 📊 适用场景

- 💬 **聊天应用** - 实时对话
- 📝 **内容生成** - 文章、代码生成
- 🎓 **教育应用** - 逐步讲解
- 🤖 **AI 助手** - 交互式问答
- 📊 **数据分析** - 实时报告生成

## 🔍 进阶功能

### 多路流式输出

```java
@GetMapping(value = "/multi", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> multiStream(@RequestParam String message) {
    Flux<String> stream1 = chatClient.prompt()
        .user("总结: " + message)
        .stream()
        .content();
    
    Flux<String> stream2 = chatClient.prompt()
        .user("扩展: " + message)
        .stream()
        .content();
    
    return Flux.merge(stream1, stream2);
}
```

### 流式转换

```java
@GetMapping(value = "/transform", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> transformStream(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content()
        .map(String::toUpperCase)  // 转换为大写
        .filter(s -> !s.isEmpty()); // 过滤空字符串
}
```

## 🐛 常见问题

**Q: 流式响应中断怎么办？**
A: 实现错误处理和重试机制，使用 `onErrorResume` 或 `retry`。

**Q: 如何控制流式速度？**
A: 使用 `delayElements` 或背压控制。

**Q: 前端如何处理流式数据？**
A: 使用 EventSource API 或 Fetch API 的 ReadableStream。

**Q: 流式响应占用资源多吗？**
A: 使用 WebFlux 的响应式模型，资源占用较少，支持高并发。

## 📚 相关资源

- [Spring WebFlux 文档](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Server-Sent Events 规范](https://html.spec.whatwg.org/multipage/server-sent-events.html)
- [Project Reactor 文档](https://projectreactor.io/docs)

## 🔗 相关项目

- [AI-Customer-Service](../AI-Customer-Service/) - 集成了流式响应的完整应用
- [Multi-LLM-Provider](../Multi-LLM-Provider/) - 多模型流式响应

---

[返回主页](../README.md)
