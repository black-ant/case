# 自定义渠道快速开始 ⚡

## 3 步开始使用

### 1️⃣ 启动项目
```bash
mvn spring-boot:run
```

### 2️⃣ 打开演示页面
双击打开 `demo.html` 文件

### 3️⃣ 填写配置并测试
在"自定义渠道"部分填写：
- **Base URL**: `https://api.openai.com/v1`
- **API Token**: `sk-xxx`（你的密钥）
- **Model**: `gpt-3.5-turbo`
- **Message**: `你好`

点击"普通对话"按钮即可！

---

## 常用配置示例

### OpenAI 官方
```
Base URL: https://api.openai.com/v1
API Token: sk-xxx
Model: gpt-3.5-turbo
```

### 本地 Ollama
```
Base URL: http://localhost:11434/v1
API Token: ollama
Model: llama2
```

### 本地 vLLM
```
Base URL: http://localhost:8000/v1
API Token: dummy
Model: meta-llama/Llama-2-7b-chat-hf
```

---

## API 测试

### 普通对话
```bash
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好",
    "baseUrl": "https://api.openai.com/v1",
    "apiToken": "sk-xxx",
    "model": "gpt-3.5-turbo"
  }'
```

### 流式对话
```bash
curl -N -X POST http://localhost:8080/api/llm/custom/stream \
  -H "Content-Type: application/json" \
  -d '{
    "message": "介绍 Spring AI",
    "baseUrl": "https://api.openai.com/v1",
    "apiToken": "sk-xxx",
    "model": "gpt-3.5-turbo"
  }'
```

---

## 📚 更多文档

- **[CUSTOM_CHANNEL_GUIDE.md](./CUSTOM_CHANNEL_GUIDE.md)** - 完整使用指南
- **[CUSTOM_CHANNEL_README.md](./CUSTOM_CHANNEL_README.md)** - 功能说明
- **[README.md](./README.md)** - 项目主文档

---

## 💡 提示

- Base URL 必须以 `/v1` 结尾
- 本地服务可以使用 `dummy` 作为 API Token
- 模型名称可选，默认使用 `gpt-3.5-turbo`
- 支持所有兼容 OpenAI API 格式的服务

---

**祝你使用愉快！** 🎉
