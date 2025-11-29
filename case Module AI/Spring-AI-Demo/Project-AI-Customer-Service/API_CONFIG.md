# API 配置管理说明

## 📦 功能概述

提供完整的 API 配置管理功能，所有配置保存到本地 JSON 文件 `data/api-config.json`，类似 SQLite 的使用方式。

## ✨ 特性

- ✅ **文件持久化** - 配置保存在 `data/api-config.json`
- ✅ **自动加载** - 应用启动时自动加载配置
- ✅ **即时保存** - 修改后立即保存到文件
- ✅ **Web 界面** - 提供友好的配置管理界面
- ✅ **API 接口** - 支持通过 REST API 管理配置
- ✅ **数据脱敏** - API Key 自动脱敏显示

## 📁 文件结构

```
Project-AI-Customer-Service/
├── data/
│   └── api-config.json           # API 配置文件
├── src/main/java/.../model/
│   └── ApiConfiguration.java     # 配置模型
├── src/main/java/.../service/
│   └── ConfigurationService.java # 配置服务
└── src/main/java/.../controller/
    └── ConfigurationController.java # 配置 API
```

## 📋 配置项说明

### Chat API 配置
- **baseUrl**: API 端点地址
- **apiKeys**: API 密钥列表（支持多个）
- **model**: 使用的模型名称
- **temperature**: 温度参数 (0-2)
- **maxTokens**: 最大 Token 数

### Embeddings API 配置
- **baseUrl**: API 端点地址
- **apiKeys**: API 密钥列表（支持多个）
- **model**: 使用的模型名称

### 系统提示词
- **systemPrompt**: AI 助手的系统提示词

### 其他配置
- **enableFunctionCalling**: 是否启用 Function Calling
- **enableStreaming**: 是否启用流式输出
- **sessionTimeout**: 会话超时时间（秒）
- **maxHistoryMessages**: 最大历史消息数

## 🌐 REST API

### 获取当前配置
```bash
GET /api/config
```

**响应示例：**
```json
{
  "success": true,
  "config": {
    "chatApi": {
      "baseUrl": "https://api.siliconflow.cn/v1",
      "apiKeys": ["sk-abc***xyz", "sk-def***uvw"],
      "model": "Qwen/Qwen2.5-7B-Instruct",
      "temperature": 0.7,
      "maxTokens": 2000
    },
    "embeddingsApi": {
      "baseUrl": "https://api.siliconflow.cn/v1",
      "apiKeys": ["sk-emb***123"],
      "model": "BAAI/bge-large-zh-v1.5"
    },
    "systemPrompt": "你是一个专业的AI客服助手...",
    "otherConfig": {
      "enableFunctionCalling": true,
      "enableStreaming": true,
      "sessionTimeout": 3600,
      "maxHistoryMessages": 10
    }
  }
}
```

### 更新完整配置
```bash
PUT /api/config
Content-Type: application/json

{
  "chatApi": { ... },
  "embeddingsApi": { ... },
  "systemPrompt": "...",
  "otherConfig": { ... }
}
```

### 更新 Chat API Keys
```bash
PUT /api/config/chat-keys
Content-Type: application/json

{
  "apiKeys": [
    "sk-xxxxxxxxxxxxxxxxxxxxxxxx",
    "sk-yyyyyyyyyyyyyyyyyyyyyyyy"
  ]
}
```

### 更新 Embeddings API Keys
```bash
PUT /api/config/embeddings-keys
Content-Type: application/json

{
  "apiKeys": [
    "sk-xxxxxxxxxxxxxxxxxxxxxxxx"
  ]
}
```

### 更新系统提示词
```bash
PUT /api/config/system-prompt
Content-Type: application/json

{
  "systemPrompt": "你是一个专业的AI客服助手..."
}
```

### 重置为默认配置
```bash
POST /api/config/reset
```

### 验证配置
```bash
GET /api/config/validate
```

### 手动保存配置
```bash
POST /api/config/save
```

## 🖥️ Web 界面

访问 `http://localhost:3000/config` 进入配置管理页面。

### 功能标签页

#### 1. Chat API 配置
- 配置 Base URL 和模型
- 设置 Temperature 和 Max Tokens
- 配置 Embeddings API
- 启用/禁用功能开关
- 会话和历史消息设置

#### 2. API Keys 管理
- 管理 Chat API Keys
- 管理 Embeddings API Keys
- 支持多个 Key（每行一个）
- 自动脱敏显示

#### 3. 系统提示词
- 编辑系统提示词
- 支持多行文本
- 实时保存

## 📄 配置文件格式

`data/api-config.json` 文件格式：

```json
{
  "chatApi": {
    "baseUrl": "https://api.siliconflow.cn/v1",
    "apiKeys": [
      "sk-xxxxxxxxxxxxxxxxxxxxxxxx",
      "sk-yyyyyyyyyyyyyyyyyyyyyyyy"
    ],
    "model": "Qwen/Qwen2.5-7B-Instruct",
    "temperature": 0.7,
    "maxTokens": 2000
  },
  "embeddingsApi": {
    "baseUrl": "https://api.siliconflow.cn/v1",
    "apiKeys": [
      "sk-xxxxxxxxxxxxxxxxxxxxxxxx"
    ],
    "model": "BAAI/bge-large-zh-v1.5"
  },
  "systemPrompt": "你是一个专业的AI客服助手，负责帮助用户解决问题。\n你应该：\n1. 友好、专业地回答用户问题\n2. 使用知识库中的信息提供准确答案\n3. 在需要时调用相关功能查询订单、物流等信息\n4. 如果不确定答案，诚实告知用户并提供其他帮助方式",
  "otherConfig": {
    "enableFunctionCalling": true,
    "enableStreaming": true,
    "sessionTimeout": 3600,
    "maxHistoryMessages": 10
  }
}
```

## 🚀 使用流程

### 1. 首次启动

```bash
# 启动应用
mvnw spring-boot:run

# 应用会自动：
# ✅ 创建 data/ 目录
# ✅ 生成默认配置文件 data/api-config.json
# ✅ 加载配置
```

### 2. 修改配置

**方式一：通过 Web 界面**
1. 访问 http://localhost:3000/config
2. 在各个标签页修改配置
3. 点击"保存"按钮
4. 配置立即保存到文件

**方式二：通过 REST API**
```bash
curl -X PUT http://localhost:8080/api/config \
  -H "Content-Type: application/json" \
  -d '{ ... }'
```

**方式三：直接编辑文件**
1. 编辑 `data/api-config.json`
2. 重启应用
3. 配置自动加载

### 3. 配置生效

- 修改后立即保存到文件
- 应用重启时自动加载
- 无需数据库

## 🔒 安全说明

### API Key 脱敏

API Key 在以下场景会自动脱敏：
- Web 界面显示
- REST API 响应
- 日志输出

**脱敏规则：**
```
原始: sk-abcdefghijklmnopqrstuvwxyz1234
脱敏: sk-abcd***1234
```

### 文件权限

建议设置 `data/api-config.json` 文件权限：
```bash
chmod 600 data/api-config.json
```

### 版本控制

`data/` 目录已添加到 `.gitignore`，不会提交到 Git。

## 🛠️ 开发指南

### 添加新配置项

1. 在 `ApiConfiguration.java` 中添加字段
2. 在 `ConfigurationService.java` 中添加更新方法
3. 在 `ConfigurationController.java` 中添加 API 端点
4. 在 Web 界面添加表单字段

### 自定义默认配置

修改 `ApiConfiguration.createDefault()` 方法：

```java
public static ApiConfiguration createDefault() {
    ApiConfiguration config = new ApiConfiguration();
    
    // 自定义默认值
    ChatApiConfig chatApi = new ChatApiConfig();
    chatApi.setBaseUrl("你的默认 URL");
    chatApi.setModel("你的默认模型");
    config.setChatApi(chatApi);
    
    return config;
}
```

## 📊 配置验证

系统会自动验证配置的有效性：

```java
// 验证必填项
- Chat API Base URL
- Chat API Keys (至少一个)
- Embeddings API Base URL
- Embeddings API Keys (至少一个)
```

调用验证 API：
```bash
GET /api/config/validate
```

## 🎯 最佳实践

1. **备份配置** - 定期备份 `data/api-config.json`
2. **多 Key 轮询** - 配置多个 API Key 提高可用性
3. **合理设置** - Temperature 和 MaxTokens 根据需求调整
4. **提示词优化** - 根据业务场景优化系统提示词
5. **定期检查** - 使用验证 API 检查配置有效性

## 🔄 迁移和部署

### 开发环境 → 测试环境
```bash
# 复制配置文件
cp data/api-config.json /path/to/test/data/

# 或通过 API 导出导入
curl http://localhost:8080/api/config > config-backup.json
curl -X PUT http://test-server:8080/api/config -d @config-backup.json
```

### 配置模板
创建配置模板供团队使用：
```bash
cp data/api-config.json config-template.json
# 移除敏感信息
# 提交到版本控制
```

## 📝 注意事项

1. **API Key 安全** - 不要将包含真实 Key 的配置文件提交到 Git
2. **文件权限** - 确保配置文件只有应用有读写权限
3. **并发修改** - 避免同时通过多种方式修改配置
4. **配置验证** - 修改后使用验证 API 确保配置有效
5. **备份重要** - 定期备份配置文件

## 🎉 总结

这个配置管理系统提供了：
- 类似 SQLite 的文件存储方式
- 友好的 Web 管理界面
- 完整的 REST API
- 自动加载和保存
- 数据脱敏保护

完美适合中小型项目的配置管理需求！
