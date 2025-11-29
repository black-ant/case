# 自定义渠道使用指南

## 🎯 什么是自定义渠道？

自定义渠道允许你使用任何兼容 OpenAI API 格式的服务，只需提供：
- **Base URL**：API 服务的基础地址
- **API Token**：认证令牌
- **Model**（可选）：模型名称

## 🚀 使用场景

### 1. 第三方 OpenAI API 代理
许多服务提供 OpenAI API 的代理或镜像服务：

```bash
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好",
    "baseUrl": "https://your-proxy.com/v1",
    "apiToken": "your-token",
    "model": "gpt-3.5-turbo"
  }'
```

### 2. 自建 LLM 服务
如果你部署了兼容 OpenAI API 的本地服务（如 vLLM、FastChat）：

```bash
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "介绍一下你自己",
    "baseUrl": "http://localhost:8000/v1",
    "apiToken": "dummy-token",
    "model": "vicuna-7b"
  }'
```

### 3. 其他云服务商
使用其他云服务商提供的 OpenAI 兼容接口：

```bash
# 示例：某云服务商
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "什么是人工智能？",
    "baseUrl": "https://api.cloud-provider.com/v1",
    "apiToken": "your-cloud-token",
    "model": "gpt-3.5-turbo"
  }'
```

### 4. 测试不同的 API 端点
快速测试和切换不同的 API 服务：

```bash
# 测试端点 A
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "测试消息",
    "baseUrl": "https://api-a.example.com/v1",
    "apiToken": "token-a",
    "model": "gpt-3.5-turbo"
  }'

# 测试端点 B
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "测试消息",
    "baseUrl": "https://api-b.example.com/v1",
    "apiToken": "token-b",
    "model": "gpt-4"
  }'
```

## 📡 API 接口

### POST /api/llm/custom/chat
普通对话接口

**请求参数**：
```json
{
  "message": "用户消息",
  "baseUrl": "API 基础地址（必填）",
  "apiToken": "API 令牌（必填）",
  "model": "模型名称（可选，默认 gpt-3.5-turbo）"
}
```

**响应示例**：
```json
{
  "provider": "Custom Channel",
  "model": "gpt-3.5-turbo",
  "response": "AI 的回复内容...",
  "duration": 1234
}
```

### POST /api/llm/custom/stream
流式对话接口

**请求参数**：同上

**响应格式**：Server-Sent Events (SSE)
```
data: 你好
data: ！
data: 我是
data: AI
...
```

## 🎨 使用 HTML 演示页面

1. 打开 `demo.html` 文件
2. 找到"自定义渠道"部分
3. 填写以下信息：
   - **Base URL**：例如 `https://api.openai.com/v1`
   - **API Token**：你的 API 密钥
   - **模型名称**：例如 `gpt-3.5-turbo`（可选）
   - **输入消息**：你想问的问题
4. 点击"普通对话"或"流式对话"按钮

## 💡 常见配置示例

### OpenAI 官方
```json
{
  "baseUrl": "https://api.openai.com/v1",
  "apiToken": "sk-xxx",
  "model": "gpt-3.5-turbo"
}
```

### 本地 vLLM 服务
```json
{
  "baseUrl": "http://localhost:8000/v1",
  "apiToken": "dummy",
  "model": "meta-llama/Llama-2-7b-chat-hf"
}
```

### 本地 Ollama（通过 OpenAI 兼容接口）
```json
{
  "baseUrl": "http://localhost:11434/v1",
  "apiToken": "ollama",
  "model": "llama2"
}
```

### FastChat 服务
```json
{
  "baseUrl": "http://localhost:8000/v1",
  "apiToken": "dummy",
  "model": "vicuna-7b"
}
```

## 🔐 安全建议

1. **不要在前端硬编码 API Token**
   - 在生产环境中，应该通过后端代理请求
   - 使用环境变量或密钥管理服务

2. **验证 Base URL**
   - 确保 Base URL 是可信的
   - 使用 HTTPS 加密传输

3. **限流和监控**
   - 实现请求限流
   - 监控 API 使用情况和成本

4. **错误处理**
   - 妥善处理网络错误
   - 提供友好的错误提示

## 🛠️ 技术实现

自定义渠道使用 Spring AI 的 `OpenAiApi` 类，支持自定义 Base URL：

```java
// 创建自定义的 OpenAI API 客户端
OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiToken);

// 创建聊天模型
OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, 
    OpenAiChatOptions.builder()
        .withModel(model)
        .withTemperature(0.7)
        .build());

// 创建 ChatClient 并执行对话
ChatClient chatClient = ChatClient.builder(chatModel).build();
```

## 🔗 兼容的服务

以下服务提供 OpenAI 兼容的 API：

- **vLLM** - 高性能 LLM 推理引擎
- **FastChat** - 开源聊天机器人平台
- **Text Generation WebUI** - 本地 LLM 界面
- **LocalAI** - OpenAI 的本地替代品
- **Ollama**（通过 OpenAI 兼容层）
- 各种云服务商的 OpenAI 兼容接口

## 📝 注意事项

1. **Base URL 格式**
   - 必须包含完整的协议（http:// 或 https://）
   - 通常以 `/v1` 结尾
   - 例如：`https://api.openai.com/v1`

2. **模型名称**
   - 不同服务支持的模型名称可能不同
   - 如果不确定，可以留空使用默认值

3. **API Token**
   - 某些本地服务可能不需要真实的 token
   - 可以使用 "dummy" 或任意字符串

4. **网络连接**
   - 确保服务器可以访问指定的 Base URL
   - 检查防火墙和网络配置

## 🎓 最佳实践

1. **环境隔离**
   - 开发环境使用测试 token
   - 生产环境使用正式 token

2. **配置管理**
   - 将常用配置保存为预设
   - 使用配置文件管理多个渠道

3. **监控和日志**
   - 记录每次 API 调用
   - 监控响应时间和错误率

4. **成本控制**
   - 设置使用限额
   - 定期审查 API 使用情况

## 🚀 扩展功能

基于自定义渠道，你可以实现：

- **多渠道负载均衡**：在多个 API 端点之间分配请求
- **自动故障转移**：主渠道失败时切换到备用渠道
- **成本优化**：根据价格选择最优渠道
- **A/B 测试**：对比不同服务的效果
- **渠道管理界面**：可视化管理多个自定义渠道

## 📚 参考资料

- [OpenAI API 文档](https://platform.openai.com/docs/api-reference)
- [vLLM 文档](https://docs.vllm.ai/)
- [FastChat 文档](https://github.com/lm-sys/FastChat)
- [Spring AI 文档](https://docs.spring.io/spring-ai/reference/)
