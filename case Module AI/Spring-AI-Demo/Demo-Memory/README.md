# Chat Memory Demo

演示使用 `ChatMemory` 与 `ChatClient` 实现有记忆的对话（与您给出的代码片段一致）。

核心代码思路：

```java
@Autowired private ChatMemory chatMemory;
@Autowired private ChatClient chatClient;
public String chat(String sessionId, String msg) {
    List<Message> history = chatMemory.get(sessionId);
    history.add(new UserMessage(msg));
    String reply = chatClient.prompt(new Prompt(history)).call().content();
    chatMemory.add(sessionId, new AssistantMessage(reply));
    return reply;
}
```

本模块提供 REST 接口：
- `POST /chat-memory/chat?sessionId={id}`，Body 为纯文本消息，返回模型回复；
- `GET /chat-memory/history?sessionId={id}`，查看当前会话历史。

运行：
- 构建运行：`mvn -q -pl Chat-Memory-Demo -am spring-boot:run`
- 端口：`8098`

> 未配置 `OPENAI_API_KEY` 时，真实调用会失败；接口结构与记忆流程可先验证，也可指向本地或其他兼容服务。

