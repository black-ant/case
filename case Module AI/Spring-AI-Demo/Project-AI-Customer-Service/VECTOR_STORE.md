# 向量存储持久化说明

## 📦 技术方案

使用 **SimpleVectorStore + 文件持久化**，类似 SQLite 的嵌入式数据库方案。

## ✨ 特性

- ✅ **零配置** - 无需安装外部服务
- ✅ **自动持久化** - 应用关闭时自动保存
- ✅ **自动加载** - 应用启动时自动加载
- ✅ **文件存储** - 数据保存在 `data/vector-store.json`
- ✅ **简单易用** - 就像使用 H2/SQLite 一样

## 📁 文件结构

```
Project-AI-Customer-Service/
├── data/                          # 数据目录（自动创建）
│   └── vector-store.json         # 向量存储文件
├── src/main/java/.../config/
│   ├── VectorStoreConfig.java    # 向量存储配置（加载）
│   └── VectorStorePersistence.java # 持久化组件（保存）
└── src/main/java/.../service/
    └── KnowledgeBaseService.java # 知识库服务
```

## 🚀 使用方式

### 1. 自动持久化

应用会自动处理数据的加载和保存：

```java
// 应用启动时
// ✅ 自动从 data/vector-store.json 加载数据

// 应用关闭时
// ✅ 自动保存数据到 data/vector-store.json
```

### 2. 手动保存（可选）

如果需要立即保存数据：

```java
@Autowired
private VectorStorePersistence vectorStorePersistence;

// 手动触发保存
vectorStorePersistence.manualSave();
```

### 3. 添加知识

```java
@Autowired
private KnowledgeBaseService knowledgeBaseService;

// 添加单条知识（自动保存）
knowledgeBaseService.addKnowledge(
    "新的产品知识内容",
    Map.of("category", "product", "type", "info")
);

// 批量添加知识（自动保存）
List<Document> documents = List.of(
    new Document("知识1", Map.of("type", "faq")),
    new Document("知识2", Map.of("type", "policy"))
);
knowledgeBaseService.addKnowledgeBatch(documents);
```

### 4. 搜索知识

```java
// 搜索相关知识
List<Document> results = knowledgeBaseService.searchKnowledge("退货政策", 3);

// 获取知识上下文
String context = knowledgeBaseService.getKnowledgeContext("配送时效");
```

## 🌐 API 接口

### 搜索知识
```bash
GET /api/knowledge/search?query=退货&topK=3
```

### 添加知识
```bash
POST /api/knowledge/add
Content-Type: application/json

{
  "content": "新的知识内容",
  "metadata": {
    "category": "policy",
    "type": "return"
  }
}
```

### 手动保存
```bash
POST /api/knowledge/save
```

### 获取统计信息
```bash
GET /api/knowledge/stats
```

## 📊 数据格式

`data/vector-store.json` 文件格式：

```json
{
  "documents": [
    {
      "id": "doc-uuid-1",
      "content": "知识内容",
      "metadata": {
        "category": "policy",
        "type": "return"
      },
      "embedding": [0.123, 0.456, ...]
    }
  ]
}
```

## 🔧 配置说明

### 数据目录

默认数据目录：`data/`

可以通过修改 `VectorStoreConfig.java` 和 `VectorStorePersistence.java` 中的路径来自定义：

```java
private static final String VECTOR_STORE_FILE = "data/vector-store.json";
```

### 初始化行为

- 如果 `data/vector-store.json` 存在 → 加载现有数据
- 如果文件不存在 → 创建新的空存储，并初始化默认知识库

## 📈 性能特点

| 指标 | 说明 |
|------|------|
| 数据规模 | 适合 < 10,000 条文档 |
| 加载速度 | 启动时一次性加载到内存 |
| 查询速度 | 内存查询，毫秒级响应 |
| 保存速度 | 序列化到 JSON，秒级完成 |
| 文件大小 | 约 1KB/文档（含向量） |

## 🎯 适用场景

✅ **适合：**
- 开发和测试环境
- 中小型知识库（< 10,000 条）
- 单机部署
- 快速原型验证

❌ **不适合：**
- 大规模生产环境（> 100,000 条）
- 分布式部署
- 高并发写入场景
- 需要复杂查询的场景

## 🔄 升级路径

如果未来需要更强大的向量数据库，可以轻松迁移到：

1. **PostgreSQL + pgvector** - 统一关系数据库
2. **Chroma** - 专业向量数据库
3. **Milvus** - 企业级向量数据库

Spring AI 的 `VectorStore` 接口保持不变，只需更换实现即可。

## 🛠️ 故障排查

### 问题：数据没有保存

**检查：**
1. 确认 `data/` 目录有写入权限
2. 查看日志中的保存信息
3. 检查应用是否正常关闭（非强制终止）

### 问题：启动时加载失败

**检查：**
1. 确认 `data/vector-store.json` 文件格式正确
2. 查看日志中的错误信息
3. 如果文件损坏，删除后重新初始化

### 问题：文件过大

**解决：**
1. 清理不需要的旧数据
2. 考虑升级到专业向量数据库
3. 实现数据分片存储

## 📝 注意事项

1. **备份数据** - 定期备份 `data/vector-store.json`
2. **版本控制** - 不要将 `data/` 目录提交到 Git（已在 .gitignore）
3. **并发写入** - SimpleVectorStore 不支持高并发写入
4. **内存占用** - 所有数据加载到内存，注意内存大小

## 🎉 总结

这个方案提供了类似 SQLite 的体验：
- 零配置，开箱即用
- 文件持久化，数据安全
- 自动加载保存，无需手动管理
- 完美适合开发测试和中小型项目

如需更强大的功能，可以随时升级到专业向量数据库！
