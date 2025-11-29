# Agent Tools Demo

演示 Spring AI 风格的 Agent + Tools 组合（包含两个 Agent），并提供 REST API 便于试用。

核心示例（与需求对齐）：

```java
Agent agent = Agent.builder()
    .chatClient(chatClient)
    .tools(List.of(weatherTool, databaseTool))
    .systemPrompt("你是智能助手，可调用工具完成任务")
    .build();
String result = agent.execute("查询北京天气并保存到数据库");
```

本 Demo 中定义了两个 Agent：
- opsAgent：可用工具 = 天气查询 + 数据库存储
- readonlyWeatherAgent：只读天气查询，不做保存

## 运行

1. 在项目根目录执行（可选）构建父工程与该模块：
   - `mvn -q -DskipTests package`
2. 运行该模块：
   - `mvn -q -pl Agent-Tools-Demo -am spring-boot:run`
3. 调用接口验证：
   - `GET http://localhost:8097/agent-tools/demo`
   - `POST http://localhost:8097/agent-tools/run?agent=ops`，Body 纯文本，例如：`查询上海天气并保存到数据库`

> 注：当前工具为本地 Stub（不依赖外部 API），LLM 调用仅作兜底；未配置 `OPENAI_API_KEY` 时也可演示工具编排流程。

