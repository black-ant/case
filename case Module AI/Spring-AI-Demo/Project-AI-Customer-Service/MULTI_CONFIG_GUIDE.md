# 多配置管理系统使用指南

## 🎯 功能概述

支持创建和管理多个 API 配置，可以根据不同环境（开发、测试、生产）或不同用途快速切换配置。

## ✨ 核心特性

- ✅ **多配置管理** - 创建任意数量的配置
- ✅ **快速切换** - 一键激活不同配置
- ✅ **配置复制** - 快速复制现有配置
- ✅ **独立管理** - 每个配置独立的 Keys、提示词等
- ✅ **文件持久化** - 所有配置保存在 `data/api-configs.json`

## 📋 使用场景

### 场景 1: 多环境管理
```
开发环境配置 → 使用测试 API Keys
测试环境配置 → 使用预发布 Keys
生产环境配置 → 使用生产 Keys
```

### 场景 2: 不同模型测试
```
GPT-3.5 配置 → 快速响应
GPT-4 配置 → 高质量回复
本地模型配置 → 私有部署
```

### 场景 3: 不同业务场景
```
客服配置 → 客服专用提示词
销售配置 → 销售话术提示词
技术支持配置 → 技术支持提示词
```

## 🌐 Web 界面操作

### 访问配置页面
```
http://localhost:3000/config
```

### 创建新配置
1. 点击"新建配置"按钮
2. 输入配置名称（如：生产环境）
3. 输入配置描述（可选）
4. 点击"创建"

### 激活配置
1. 在配置列表中找到要使用的配置
2. 点击"激活"按钮
3. 该配置立即生效

### 编辑配置
1. 点击配置的"编辑"按钮
2. 修改配置参数
3. 保存更改

### 复制配置
1. 点击配置的"复制"按钮
2. 自动创建副本
3. 可以基于副本进行修改

### 删除配置
1. 点击配置的"删除"按钮
2. 确认删除
3. 注意：活动配置无法删除

## 🔧 REST API 使用

### 获取所有配置
```bash
GET /api/config

Response:
{
  "success": true,
  "configurations": [
    {
      "id": "uuid-1",
      "name": "开发环境",
      "description": "开发测试用",
      "active": true,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "chatApi": { ... },
      "embeddingsApi": { ... },
      "systemPrompt": "...",
      "otherConfig": { ... }
    }
  ]
}
```

### 获取活动配置
```bash
GET /api/config/active

Response:
{
  "success": true,
  "config": { ... }
}
```

### 创建新配置
```bash
POST /api/config
Content-Type: application/json

{
  "name": "生产环境",
  "description": "生产环境配置"
}

Response:
{
  "success": true,
  "message": "配置创建成功",
  "config": { ... }
}
```

### 更新配置
```bash
PUT /api/config/{id}
Content-Type: application/json

{
  "name": "更新后的名称",
  "description": "更新后的描述",
  "chatApi": {
    "baseUrl": "https://api.example.com/v1",
    "apiKeys": ["sk-xxx", "sk-yyy"],
    "model": "gpt-4",
    "temperature": 0.7,
    "maxTokens": 2000
  },
  ...
}

Response:
{
  "success": true,
  "message": "配置更新成功"
}
```

### 激活配置
```bash
POST /api/config/{id}/activate

Response:
{
  "success": true,
  "message": "配置已激活"
}
```

### 复制配置
```bash
POST /api/config/{id}/duplicate
Content-Type: application/json

{
  "name": "新配置名称"
}

Response:
{
  "success": true,
  "message": "配置复制成功",
  "config": { ... }
}
```

### 删除配置
```bash
DELETE /api/config/{id}

Response:
{
  "success": true,
  "message": "配置删除成功"
}
```

### 验证配置
```bash
GET /api/config/{id}/validate

Response:
{
  "success": true,
  "valid": true,
  "message": "配置有效"
}
```

## 📁 配置文件格式

`data/api-configs.json`:

```json
{
  "activeConfigId": "uuid-1",
  "configurations": [
    {
      "id": "uuid-1",
      "name": "开发环境",
      "description": "开发测试用",
      "active": true,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
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
      "systemPrompt": "你是一个专业的AI客服助手...",
      "otherConfig": {
        "enableFunctionCalling": true,
        "enableStreaming": true,
        "sessionTimeout": 3600,
        "maxHistoryMessages": 10
      }
    },
    {
      "id": "uuid-2",
      "name": "生产环境",
      "description": "生产环境配置",
      "active": false,
      ...
    }
  ]
}
```

## 🚀 快速开始

### 1. 启动应用
```bash
mvnw spring-boot:run
cd web && npm start
```

### 2. 访问配置页面
```
http://localhost:3000/config
```

### 3. 创建第一个配置
- 点击"新建配置"
- 输入名称和描述
- 点击"创建"

### 4. 编辑配置
- 点击"编辑"按钮
- 配置 API Keys、模型等参数
- 保存更改

### 5. 激活配置
- 点击"激活"按钮
- 配置立即生效

## 💡 最佳实践

### 1. 命名规范
```
✅ 好的命名：
- 开发环境
- 生产环境 - GPT-4
- 测试环境 - 本地模型

❌ 不好的命名：
- 配置1
- test
- aaa
```

### 2. 配置组织
```
按环境分类：
├── 开发环境
├── 测试环境
└── 生产环境

按模型分类：
├── GPT-3.5 配置
├── GPT-4 配置
└── 本地模型配置

按业务分类：
├── 客服配置
├── 销售配置
└── 技术支持配置
```

### 3. 安全建议
- ✅ 定期备份配置文件
- ✅ 不同环境使用不同的 API Keys
- ✅ 生产环境配置设置更严格的参数
- ✅ 定期检查和更新 API Keys
- ❌ 不要在配置描述中包含敏感信息

### 4. 性能优化
- 为高频使用场景创建专用配置
- 根据需求调整 Temperature 和 MaxTokens
- 使用多个 API Keys 实现负载均衡

## 🔄 配置切换流程

```
1. 用户点击"激活"按钮
   ↓
2. 前端调用 POST /api/config/{id}/activate
   ↓
3. 后端更新活动配置 ID
   ↓
4. 保存到 data/api-configs.json
   ↓
5. 返回成功响应
   ↓
6. 前端刷新配置列表
   ↓
7. 新配置立即生效
```

## 📊 配置状态说明

| 状态 | 说明 | 操作 |
|------|------|------|
| 活动 | 当前正在使用的配置 | 可编辑，不可删除 |
| 非活动 | 备用配置 | 可激活、编辑、删除 |

## 🛠️ 故障排查

### 问题：配置无法激活
**原因：** 配置可能无效
**解决：** 
1. 检查 API Keys 是否填写
2. 检查 Base URL 是否正确
3. 使用验证 API 检查配置

### 问题：配置无法删除
**原因：** 尝试删除活动配置
**解决：** 先激活其他配置，再删除

### 问题：配置丢失
**原因：** 配置文件损坏或删除
**解决：** 
1. 检查 `data/api-configs.json` 是否存在
2. 从备份恢复
3. 重新创建配置

## 📝 注意事项

1. **活动配置** - 同一时间只能有一个活动配置
2. **配置删除** - 活动配置无法删除，需先切换
3. **文件备份** - 定期备份 `data/api-configs.json`
4. **版本控制** - 不要将配置文件提交到 Git
5. **配置验证** - 激活前验证配置有效性

## 🎉 总结

多配置管理系统让您可以：
- 轻松管理多个环境的配置
- 快速切换不同的 API 设置
- 安全地测试新配置
- 提高开发和运维效率

开始使用多配置管理，让您的 AI 应用更加灵活！
