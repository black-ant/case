# 自定义渠道功能说明

## ✨ 新增功能

Multi-LLM-Provider 项目现在支持**自定义渠道**功能，允许你连接任何兼容 OpenAI API 格式的服务！

## 🎯 核心特性

- ✅ 动态配置 Base URL 和 API Token
- ✅ 支持普通对话和流式对话
- ✅ 兼容所有 OpenAI API 格式的服务
- ✅ 可视化 HTML 界面
- ✅ 完整的 REST API

## 📁 新增文件

```
Multi-LLM-Provider/
├── src/main/java/com/example/multillm/
│   ├── service/
│   │   └── CustomChannelService.java          # 自定义渠道服务
│   └── model/
│       └── CustomChannelRequest.java          # 请求模型
├── CUSTOM_CHANNEL_GUIDE.md                    # 详细使用指南
├── test-custom-channel.sh                     # Linux/Mac 测试脚本
└── test-custom-channel.bat                    # Windows 测试脚本
```

## 🚀 快速开始

### 1. 使用 HTML 界面（推荐）

1. 启动项目：`mvn spring-boot:run`
2. 打开 `demo.html` 文件
3. 找到"自定义渠道"部分
4. 填写配置信息：
   - **Base URL**：例如 `https://api.openai.com/v1`
   - **API Token**：你的 API 密钥
   - **模型名称**：例如 `gpt-3.5-turbo`
   - **输入消息**：你的问题
5. 点击"普通对话"或"流式对话"

### 2. 使用 curl 命令

```bash
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，请介绍一下自己",
    "baseUrl": "https://api.openai.com/v1",
    "apiToken": "sk-xxx",
    "model": "gpt-3.5-turbo"
  }'
```

### 3. 使用测试脚本

**Linux/Mac:**
```bash
# 编辑 test-custom-channel.sh，修改配置
chmod +x test-custom-channel.sh
./test-custom-channel.sh
```

**Windows:**
```cmd
# 编辑 test-custom-channel.bat，修改配置
test-custom-channel.bat
```

## 💡 使用场景

### 1. OpenAI 官方 API
```json
{
  "baseUrl": "https://api.openai.com/v1",
  "apiToken": "sk-xxx",
  "model": "gpt-3.5-turbo"
}
```

### 2. 第三方代理服务
```json
{
  "baseUrl": "https://your-proxy.com/v1",
  "apiToken": "your-token",
  "model": "gpt-3.5-turbo"
}
```

### 3. 本地 vLLM 服务
```json
{
  "baseUrl": "http://localhost:8000/v1",
  "apiToken": "dummy",
  "model": "meta-llama/Llama-2-7b-chat-hf"
}
```

### 4. 本地 Ollama（OpenAI 兼容模式）
```json
{
  "baseUrl": "http://localhost:11434/v1",
  "apiToken": "ollama",
  "model": "llama2"
}
```

### 5. FastChat 服务
```json
{
  "baseUrl": "http://localhost:8000/v1",
  "apiToken": "dummy",
  "model": "vicuna-7b"
}
```

## 📡 API 接口

### POST /api/llm/custom/chat
普通对话接口

**请求体：**
```json
{
  "message": "用户消息（必填）",
  "baseUrl": "API 基础地址（必填）",
  "apiToken": "API 令牌（必填）",
  "model": "模型名称（可选，默认 gpt-3.5-turbo）"
}
```

**响应：**
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

**请求体：** 同上

**响应：** Server-Sent Events (SSE)

## 🎨 HTML 界面更新

`demo.html` 新增了自定义渠道卡片，包含：
- Base URL 输入框
- API Token 输入框（密码类型）
- 模型名称输入框
- 消息输入框
- 普通对话和流式对话按钮
- 响应显示区域

## 🔧 技术实现

使用 Spring AI 的 `OpenAiApi` 类实现动态配置：

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

## 📚 文档

- **[CUSTOM_CHANNEL_GUIDE.md](./CUSTOM_CHANNEL_GUIDE.md)** - 完整的使用指南
- **[README.md](./README.md)** - 项目主文档

## 🔐 安全提示

1. **不要在前端硬编码 API Token**
2. **使用 HTTPS 加密传输**
3. **实现请求限流和监控**
4. **验证 Base URL 的可信性**

## 🎓 最佳实践

1. **环境隔离**：开发和生产使用不同的配置
2. **配置管理**：将常用配置保存为预设
3. **监控日志**：记录每次 API 调用
4. **成本控制**：设置使用限额

## 🚀 扩展功能建议

基于自定义渠道，你可以实现：
- 多渠道负载均衡
- 自动故障转移
- 成本优化策略
- A/B 测试
- 渠道管理界面

## 📝 更新日志

### v1.1.0 (2025-01-07)
- ✅ 新增自定义渠道功能
- ✅ 支持动态配置 Base URL 和 API Token
- ✅ 更新 HTML 演示界面
- ✅ 添加测试脚本
- ✅ 完善文档

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

MIT License
