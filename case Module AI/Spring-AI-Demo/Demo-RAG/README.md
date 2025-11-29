# RAG Demo - 检索增强生成示例

这个项目展示了如何使用 Spring AI 实现 RAG (Retrieval-Augmented Generation) 功能，通过向量数据库检索相关文档来增强 AI 的回答能力。

## 🎯 功能特性

- ✅ 文档向量化存储
- ✅ 语义相似度搜索
- ✅ 上下文增强回答
- ✅ 支持多种文档格式（PDF、TXT）
- ✅ PGVector 和 SimpleVectorStore 支持
- ✅ 文档分块策略

## 🏗️ 技术架构

### 核心组件

1. **VectorStore** - 向量存储
   - SimpleVectorStore (内存)
   - PGVector (PostgreSQL)

2. **DocumentReader** - 文档读取
   - PDF Reader
   - Tika Document Reader

3. **EmbeddingModel** - 向量化模型
   - OpenAI Embeddings

## 📦 依赖项

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pdf-document-reader</artifactId>
</dependency>
```

## 🚀 快速开始

### 1. 配置环境

```bash
export OPENAI_API_KEY=your-api-key
```

### 2. 启动应用

```bash
cd RAG-Demo
mvn spring-boot:run
```

### 3. 访问接口

```bash
# 上传文档
curl -X POST http://localhost:8081/api/rag/upload \
  -F "file=@document.pdf"

# 查询
curl -X POST http://localhost:8081/api/rag/query \
  -H "Content-Type: application/json" \
  -d '{"question": "什么是 RAG？"}'
```

## 💡 核心概念

### RAG 工作流程

```
1. 文档处理
   ├─ 读取文档
   ├─ 文档分块
   └─ 向量化

2. 存储
   └─ 保存到向量数据库

3. 检索
   ├─ 用户提问
   ├─ 问题向量化
   ├─ 相似度搜索
   └─ 获取相关文档

4. 生成
   ├─ 构建上下文
   ├─ 调用 LLM
   └─ 返回答案
```

### 文档分块策略

- **固定大小分块**: 按字符数分割
- **段落分块**: 按段落分割
- **语义分块**: 按语义边界分割

## 📝 使用示例

### 示例 1: 上传并查询文档

```java
// 上传文档
@PostMapping("/upload")
public String uploadDocument(@RequestParam("file") MultipartFile file) {
    Resource resource = new InputStreamResource(file.getInputStream());
    List<Document> documents = pdfReader.read(resource);
    vectorStore.add(documents);
    return "文档上传成功";
}

// 查询
@PostMapping("/query")
public String query(@RequestBody QueryRequest request) {
    List<Document> similarDocs = vectorStore.similaritySearch(
        SearchRequest.query(request.getQuestion()).withTopK(3)
    );
    
    String context = similarDocs.stream()
        .map(Document::getContent)
        .collect(Collectors.joining("\n"));
    
    String prompt = String.format(
        "基于以下上下文回答问题：\n%s\n\n问题：%s",
        context, request.getQuestion()
    );
    
    return chatClient.prompt(prompt).call().content();
}
```

### 示例 2: 自定义文档元数据

```java
Document doc = new Document(
    "文档内容",
    Map.of(
        "source", "manual.pdf",
        "page", 1,
        "category", "技术文档"
    )
);
vectorStore.add(List.of(doc));
```

## 🔧 配置说明

### application.yml

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      embedding:
        options:
          model: text-embedding-3-small
  
  datasource:
    url: jdbc:postgresql://localhost:5432/vectordb
    username: postgres
    password: password
```

## 🎓 最佳实践

### 1. 文档分块
- 块大小: 500-1000 字符
- 重叠: 50-100 字符
- 保留上下文完整性

### 2. 向量检索
- Top-K: 3-5 个结果
- 相似度阈值: 0.7+
- 结果去重

### 3. 上下文构建
- 限制总长度
- 按相关性排序
- 添加来源信息

### 4. 性能优化
- 批量向量化
- 使用索引
- 缓存常用查询

## 📊 适用场景

- 📚 企业知识库问答
- 📄 文档智能检索
- 🔍 语义搜索引擎
- 💼 客服知识库
- 📖 学习资料助手

## 🔍 进阶功能

### 混合检索

结合关键词检索和向量检索：

```java
// 关键词检索
List<Document> keywordResults = keywordSearch(query);

// 向量检索
List<Document> vectorResults = vectorStore.similaritySearch(query);

// 合并结果
List<Document> finalResults = mergeResults(keywordResults, vectorResults);
```

### 重排序

使用重排序模型提升结果质量：

```java
List<Document> reranked = reranker.rerank(query, documents);
```

## 🐛 常见问题

**Q: 向量数据库如何选择？**
A: 开发环境用 SimpleVectorStore，生产环境推荐 PGVector 或 Pinecone。

**Q: 如何提高检索准确性？**
A: 优化分块策略、调整 Top-K 值、使用重排序。

**Q: 支持哪些文档格式？**
A: PDF、TXT、Word、Markdown 等，通过不同的 DocumentReader 实现。

## 📚 相关资源

- [Spring AI Vector Store 文档](https://docs.spring.io/spring-ai/reference/api/vectordbs.html)
- [PGVector 文档](https://github.com/pgvector/pgvector)
- [RAG 最佳实践](https://www.pinecone.io/learn/retrieval-augmented-generation/)

## 🔗 相关项目

- [AI-Customer-Service](../AI-Customer-Service/) - 集成了 RAG 的完整应用
- [Function-Calling-Demo](../Function-Calling-Demo/) - 可与 RAG 结合使用

---

[返回主页](../README.md)
