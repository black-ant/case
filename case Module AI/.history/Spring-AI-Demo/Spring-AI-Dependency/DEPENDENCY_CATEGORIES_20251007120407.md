# Spring AI 依赖分类详解

本文档详细介绍 Spring AI 0.8.1 版本中所有官方依赖的分类、用途和选择建议。

## 📑 目录

1. [基础核心依赖](#1-基础核心依赖)
2. [Spring Boot Starter](#2-spring-boot-starter)
3. [LLM 提供商](#3-llm-提供商)
4. [向量存储](#4-向量存储)
5. [文档加载器](#5-文档加载器)
6. [实用工具](#6-实用工具)
7. [测试依赖](#7-测试依赖)

---

## 1. 基础核心依赖

### 1.1 spring-ai-core

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-core</artifactId>
</dependency>
```

**说明:**
- Spring AI 的核心抽象层
- 定义了所有关键接口：`ChatModel`、`EmbeddingModel`、`VectorStore` 等
- 所有其他依赖的基础

**何时使用:**
- 几乎所有 Spring AI 项目都需要
- 通常通过 starter 自动引入，无需手动添加

---

### 1.2 spring-ai-annotations

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-annotations</artifactId>
</dependency>
```

**说明:**
- 提供 Spring AI 专用注解
- 从 0.8.0 版本开始独立出来

**主要注解:**
- `@ChatMessage` - 标记聊天消息
- `@AIFunction` - 标记 AI 函数

---

### 1.3 spring-ai-bom

**Maven 坐标:**
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>0.8.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**说明:**
- Bill of Materials（物料清单）
- 统一管理所有 Spring AI 依赖的版本
- **强烈推荐使用**

**优点:**
- 避免版本冲突
- 简化依赖管理
- 确保兼容性

---

## 2. Spring Boot Starter

### 2.1 spring-ai-spring-boot-starter

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-spring-boot-starter</artifactId>
</dependency>
```

**说明:**
- Spring AI 的统一入口 Starter
- 包含核心功能和自动配置
- 大多数项目的起点

**包含内容:**
- `spring-ai-core`
- 自动配置类
- 基础工具类

---

### 2.2 spring-ai-webmvc-starter

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-webmvc-starter</artifactId>
</dependency>
```

**说明:**
- 提供 REST API 端点
- 自动生成 `/ai/**` 控制器
- 适合构建 AI 服务平台

**使用场景:**
- 构建 AI API 服务
- 模型调用平台（MCP）
- 微服务架构

---

### 2.3 spring-ai-webflux-starter

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-webflux-starter</artifactId>
</dependency>
```

**说明:**
- WebFlux 版本的 REST API
- 响应式编程支持
- 适合高并发场景

---

## 3. LLM 提供商

### 3.1 OpenAI

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

**支持模型:**
- GPT-4, GPT-4 Turbo
- GPT-3.5 Turbo
- DALL·E (图像生成)
- Whisper (语音识别)

**配置示例:**
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4
          temperature: 0.7
```

**优点:**
- 最成熟的 API
- 模型性能优秀
- 文档完善

**缺点:**
- 成本较高
- 需要网络访问
- 数据隐私考虑

---

### 3.2 Azure OpenAI

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai-spring-boot-starter</artifactId>
</dependency>
```

**配置示例:**
```yaml
spring:
  ai:
    azure:
      openai:
        api-key: ${AZURE_OPENAI_API_KEY}
        endpoint: ${AZURE_OPENAI_ENDPOINT}
        chat:
          options:
            deployment-name: gpt-4
```

**优点:**
- 企业级 SLA
- 数据隐私保护
- 区域部署
- Azure 生态集成

**适用场景:**
- 企业应用
- 合规要求高
- 已使用 Azure

---

### 3.3 Ollama

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>
```

**支持模型:**
- Llama 3 / 3.1
- Mistral
- Gemma
- Qwen
- DeepSeek

**配置示例:**
```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3
```

**优点:**
- 完全本地运行
- 无 API 成本
- 数据隐私
- 支持多种开源模型

**适用场景:**
- 开发测试
- 离线环境
- 数据敏感场景
- 成本控制

---

### 3.4 Anthropic (Claude)

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-anthropic-spring-boot-starter</artifactId>
</dependency>
```

**支持模型:**
- Claude 3 Opus
- Claude 3 Sonnet
- Claude 3 Haiku

**特点:**
- 长上下文支持（200K tokens）
- 安全性好
- 推理能力强
- 代码生成优秀

---

### 3.5 Google Gemini

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-gemini-spring-boot-starter</artifactId>
</dependency>
```

**支持模型:**
- Gemini Pro
- Gemini Pro Vision

**特点:**
- 多模态能力
- 长上下文
- 成本相对较低

---

### 3.6 其他提供商

| 提供商 | Artifact ID | 特点 |
|--------|-------------|------|
| Cohere | `spring-ai-cohere-spring-boot-starter` | 企业级 NLP |
| Mistral AI | `spring-ai-mistral-spring-boot-starter` | 欧洲开源模型 |
| HuggingFace | `spring-ai-huggingface-spring-boot-starter` | 开源模型平台 |
| Vertex AI | `spring-ai-vertexai-spring-boot-starter` | Google Cloud AI |

---

## 4. 向量存储

### 4.1 PGVector (PostgreSQL)

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
</dependency>
```

**配置示例:**
```yaml
spring:
  ai:
    vectorstore:
      pgvector:
        host: localhost
        port: 5432
        database: vectordb
        dimensions: 1536
```

**性能指标:**
- 向量维度：最高 2000
- 数据规模：百万级
- 查询延迟：10-100ms

**优点:**
- 开源免费
- SQL 支持
- 成熟稳定
- 易于运维

**适用场景:**
- 中小规模应用
- 已使用 PostgreSQL
- 需要 SQL 查询

---

### 4.2 Milvus

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-milvus-store-spring-boot-starter</artifactId>
</dependency>
```

**性能指标:**
- 向量维度：最高 32768
- 数据规模：十亿级
- 查询延迟：<10ms

**优点:**
- 开源
- 高性能
- 分布式
- 大规模支持

**适用场景:**
- 大规模应用
- 需要自建
- 高性能要求

---

### 4.3 向量数据库对比

| 数据库 | 规模 | 性能 | 成本 | 运维难度 | 推荐场景 |
|--------|------|------|------|----------|----------|
| PGVector | 百万级 | 中 | 低 | 低 | 中小型应用 |
| Milvus | 十亿级 | 高 | 中 | 高 | 大规模应用 |
| Qdrant | 千万级 | 高 | 中 | 中 | 高性能需求 |
| Chroma | 十万级 | 低 | 低 | 低 | 开发测试 |
| Redis | 百万级 | 极高 | 高 | 低 | 低延迟需求 |
| Elasticsearch | 千万级 | 中 | 中 | 中 | 已有 ES 环境 |

---

## 5. 文档加载器

### 5.1 PDF 文档加载器

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-document-loader-pdf-spring-boot-starter</artifactId>
</dependency>
```

**特点:**
- 基于 Apache PDFBox
- 支持文本提取
- 保留页面信息

---

### 5.2 Word 文档加载器

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-document-loader-docx-spring-boot-starter</artifactId>
</dependency>
```

**特点:**
- 基于 Apache POI
- 支持 .docx 格式
- 提取文本和样式

---

### 5.3 文档加载器对比

| 加载器 | 支持格式 | 依赖大小 | 性能 | 推荐使用 |
|--------|----------|----------|------|----------|
| PDF | PDF | 中 | 中 | 通用文档 |
| DOCX | Word | 大 | 中 | Office 文档 |
| Markdown | MD | 小 | 高 | 技术文档 |
| Web | HTML | 小 | 中 | 网页内容 |

---

## 6. 实用工具

### 6.1 Prompt Template

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-prompt-template</artifactId>
</dependency>
```

**功能:**
- 模板化 Prompt
- 变量替换
- 条件渲染

**示例:**
```java
PromptTemplate template = new PromptTemplate(
    "请用{language}回答：{question}"
);
Prompt prompt = template.create(Map.of(
    "language", "中文",
    "question", "什么是 Spring AI"
));
```

---

### 6.2 Evaluation

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-evaluation</artifactId>
</dependency>
```

**功能:**
- 模型评估
- 性能测试
- 质量分析

---

### 6.3 Agent Framework

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-agent</artifactId>
</dependency>
```

**功能:**
- Agent 模式
- 工具调用
- 链式思考

---

## 7. 测试依赖

### 7.1 spring-ai-test

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-test</artifactId>
    <scope>test</scope>
</dependency>
```

**功能:**
- Mock ChatModel
- Mock EmbeddingModel
- 测试工具类

---

### 7.2 spring-ai-boot-test-starter

**Maven 坐标:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-boot-test-starter</artifactId>
    <scope>test</scope>
</dependency>
```

**功能:**
- Spring Boot 测试集成
- 自动配置测试环境

---

## 📊 依赖选择决策树

```
开始
├─ 需要聊天功能？
│  ├─ 是 → 选择 LLM 提供商
│  │  ├─ 本地开发 → Ollama
│  │  ├─ 生产环境 → Azure OpenAI / AWS Bedrock
│  │  └─ 原型验证 → OpenAI
│  └─ 否 → 继续
│
├─ 需要向量搜索？
│  ├─ 是 → 选择向量数据库
│  │  ├─ 小规模 → PGVector / Chroma
│  │  ├─ 大规模 → Milvus / Pinecone
│  │  └─ 低延迟 → Redis
│  └─ 否 → 继续
│
├─ 需要文档处理？
│  ├─ 是 → 选择文档加载器
│  │  ├─ PDF → PDF Loader
│  │  ├─ Word → DOCX Loader
│  │  └─ 网页 → Web Loader
│  └─ 否 → 继续
│
└─ 完成
```

---

## 💡 最佳实践

1. **使用 BOM 管理版本**
2. **按需引入依赖**（不要全部引入）
3. **开发环境使用 Ollama**
4. **生产环境使用企业级服务**
5. **合理选择向量数据库**
6. **做好错误处理和重试**
7. **监控 API 调用和成本**

---

## 📚 参考资源

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/)
- [Maven Central](https://central.sonatype.com/search?q=spring-ai)
- [GitHub Issues](https://github.com/spring-projects/spring-ai/issues)
