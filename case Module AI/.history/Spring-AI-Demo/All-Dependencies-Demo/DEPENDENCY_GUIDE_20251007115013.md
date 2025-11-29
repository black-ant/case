# Spring AI 依赖详细指南

## 📋 目录

1. [核心依赖](#核心依赖)
2. [LLM 提供商对比](#llm-提供商对比)
3. [向量数据库对比](#向量数据库对比)
4. [依赖版本管理](#依赖版本管理)
5. [常见问题](#常见问题)

## 核心依赖

### Spring AI BOM (Bill of Materials)

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>1.0.0-M4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

使用 BOM 可以统一管理所有 Spring AI 依赖的版本，避免版本冲突。

## LLM 提供商对比

### OpenAI

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 最成熟的 API
- 模型性能优秀 (GPT-4, GPT-4o)
- 文档完善
- 社区支持好

**缺点：**
- 成本较高
- 数据隐私考虑
- 需要网络访问

**适用场景：**
- 原型开发
- 高质量对话应用
- 需要最新模型能力

**配置示例：**
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o
          temperature: 0.7
```

---

### Azure OpenAI

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 企业级 SLA
- 数据隐私保护
- 区域部署
- 与 Azure 生态集成

**缺点：**
- 需要 Azure 账号
- 配置相对复杂
- 模型更新较慢

**适用场景：**
- 企业应用
- 合规要求高
- 已使用 Azure 生态

**配置示例：**
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

---

### Anthropic (Claude)

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-anthropic-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 长上下文支持 (200K tokens)
- 安全性好
- 推理能力强
- 代码生成优秀

**缺点：**
- API 相对较新
- 生态不如 OpenAI 完善

**适用场景：**
- 需要长上下文
- 代码生成
- 安全敏感应用

**配置示例：**
```yaml
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-3-opus-20240229
```

---

### Ollama (本地模型)

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 完全本地运行
- 无 API 成本
- 数据隐私
- 支持多种开源模型

**缺点：**
- 需要本地资源
- 性能依赖硬件
- 模型能力相对较弱

**适用场景：**
- 开发测试
- 离线环境
- 数据敏感场景
- 成本控制

**配置示例：**
```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3
```

**支持的模型：**
- Llama 3 / 3.1
- Mistral
- Gemma
- Qwen
- DeepSeek

---

### Google Vertex AI (Gemini)

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-gemini-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 多模态能力强
- 与 GCP 集成
- 长上下文支持
- 成本相对较低

**缺点：**
- 需要 GCP 账号
- 配置复杂
- 区域限制

**适用场景：**
- 多模态应用
- 使用 GCP 生态
- 图像/视频理解

---

### AWS Bedrock

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-bedrock-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 多模型选择
- 与 AWS 集成
- 企业级支持
- 区域部署

**缺点：**
- 需要 AWS 账号
- 成本计算复杂

**适用场景：**
- AWS 生态
- 需要多模型切换
- 企业应用

**支持的模型：**
- Claude (Anthropic)
- Llama (Meta)
- Titan (Amazon)
- Jurassic (AI21)

---

## 向量数据库对比

### PGVector (PostgreSQL)

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 开源免费
- SQL 支持
- 成熟稳定
- 易于运维

**缺点：**
- 性能不如专用向量数据库
- 扩展性有限

**适用场景：**
- 中小规模应用
- 已使用 PostgreSQL
- 需要 SQL 查询

**性能指标：**
- 向量维度：最高 2000
- 数据规模：百万级
- 查询延迟：10-100ms

---

### Chroma

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-chroma-store-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 轻量级
- 易于使用
- 适合开发

**缺点：**
- 生产环境支持有限
- 性能一般

**适用场景：**
- 开发测试
- 原型验证
- 小规模应用

---

### Pinecone

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pinecone-store-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 云原生
- 高性能
- 托管服务
- 易于扩展

**缺点：**
- 成本较高
- 供应商锁定

**适用场景：**
- 大规模应用
- 需要高性能
- 不想自己运维

**性能指标：**
- 向量维度：最高 20000
- 数据规模：亿级
- 查询延迟：<50ms

---

### Milvus

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-milvus-store-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 开源
- 高性能
- 分布式
- 大规模支持

**缺点：**
- 运维复杂
- 资源消耗大

**适用场景：**
- 大规模应用
- 需要自建
- 高性能要求

**性能指标：**
- 向量维度：最高 32768
- 数据规模：十亿级
- 查询延迟：<10ms

---

### Redis

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-redis-store-spring-boot-starter</artifactId>
</dependency>
```

**优点：**
- 内存存储
- 极快速度
- 易于集成
- 成熟稳定

**缺点：**
- 成本较高（内存）
- 数据规模受限

**适用场景：**
- 需要极低延迟
- 已使用 Redis
- 中小规模数据

---

### Simple Vector Store

**依赖：**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-simple-vector-store</artifactId>
</dependency>
```

**优点：**
- 无需外部依赖
- 配置简单
- 适合开发

**缺点：**
- 仅内存/文件存储
- 不适合生产
- 性能有限

**适用场景：**
- 开发测试
- 快速原型
- 学习示例

---

## 依赖版本管理

### 当前版本

- Spring Boot: 3.2.0
- Spring AI: 1.0.0-M4 (Milestone 4)

### 版本兼容性

| Spring AI | Spring Boot | Java |
|-----------|-------------|------|
| 1.0.0-M4  | 3.2.x       | 17+  |
| 1.0.0-M3  | 3.1.x       | 17+  |
| 1.0.0-M2  | 3.1.x       | 17+  |

### 升级建议

1. **使用 BOM 管理版本**
   - 避免手动指定每个依赖的版本
   - 确保版本兼容性

2. **关注 Release Notes**
   - Spring AI 仍在快速迭代
   - 注意 Breaking Changes

3. **测试后再升级**
   - 在测试环境验证
   - 检查 API 变化

---

## 常见问题

### Q1: 如何选择 LLM 提供商？

**考虑因素：**
1. **成本**：OpenAI > Azure OpenAI > Anthropic > Ollama (免费)
2. **性能**：GPT-4 > Claude 3 > Gemini > Llama 3
3. **隐私**：Ollama > Azure OpenAI > AWS Bedrock > OpenAI
4. **易用性**：OpenAI > Ollama > Azure OpenAI > AWS Bedrock

**建议：**
- 开发测试：Ollama
- 原型验证：OpenAI
- 生产环境：Azure OpenAI 或 AWS Bedrock
- 成本敏感：Ollama 或 Gemini

---

### Q2: 如何选择向量数据库？

**考虑因素：**
1. **数据规模**：
   - <10万：Simple Vector Store
   - 10万-100万：PGVector, Redis
   - >100万：Pinecone, Milvus

2. **性能要求**：
   - 低延迟：Redis, Pinecone
   - 高吞吐：Milvus
   - 平衡：PGVector

3. **运维能力**：
   - 托管服务：Pinecone
   - 自建：PGVector, Milvus
   - 简单：Chroma, Simple Vector Store

---

### Q3: 依赖冲突怎么办？

**解决方案：**

1. **使用 BOM**
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>1.0.0-M4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

2. **排除冲突依赖**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>conflicting-group</groupId>
            <artifactId>conflicting-artifact</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

3. **查看依赖树**
```bash
mvn dependency:tree
```

---

### Q4: 如何减小依赖体积？

**优化建议：**

1. **只引入需要的依赖**
   - 不要引入所有 LLM 提供商
   - 选择合适的向量数据库

2. **使用 Starter**
   - Spring Boot Starter 已优化依赖
   - 避免直接引入底层库

3. **排除不需要的传递依赖**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-tika-document-reader</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.apache.tika</groupId>
            <artifactId>tika-parsers-standard-package</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

---

### Q5: 生产环境推荐配置？

**推荐组合 1：企业级 RAG**
```xml
<!-- Azure OpenAI + PGVector + Tika -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-tika-document-reader</artifactId>
</dependency>
```

**推荐组合 2：高性能应用**
```xml
<!-- OpenAI + Pinecone + PDF -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pinecone-store-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pdf-document-reader</artifactId>
</dependency>
```

**推荐组合 3：成本优化**
```xml
<!-- Ollama + Redis + Text -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-redis-store-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-text-document-reader</artifactId>
</dependency>
```

---

## 📚 参考资源

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/)
- [Maven Central - Spring AI](https://central.sonatype.com/search?q=spring-ai)
- [Spring AI GitHub Issues](https://github.com/spring-projects/spring-ai/issues)
