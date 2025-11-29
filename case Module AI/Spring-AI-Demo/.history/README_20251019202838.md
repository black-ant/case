# Spring AI 示例项目集合

这是一个完整的 Spring AI 示例项目集合，包含多个独立的 Demo 项目，展示了 Spring AI 的各种核心功能和最佳实践。

## 📚 项目概览

本仓库包含以下项目：

### 🎯 完整应用示例

| 项目 | 描述 | 核心功能 |
|------|------|---------|
| [AI-Customer-Service](./AI-Customer-Service/) | **AI 智能客服系统** | 集成所有功能的完整应用 |
| [Sample-Spring-AI](./Sample-Spring-AI/) | Spring AI 基础示例 | 基本对话、流式响应 |

### 🔧 功能专项 Demo

| 项目 | 描述 | 学习重点 |
|------|------|---------|
| [RAG-Demo](./RAG-Demo/) | 检索增强生成 | 向量存储、文档检索、知识库 |
| [Function-Calling-Demo](./Function-Calling-Demo/) | 工具调用 | Function Calling、工具集成 |
| [Streaming-Demo](./Streaming-Demo/) | 流式响应 | SSE、WebFlux、实时输出 |
| [Prompt-Engineering-Demo](./Prompt-Engineering-Demo/) | 提示工程 | Prompt 模板、角色设定 |
| [Agent-Workflow-Demo](./Agent-Workflow-Demo/) | 智能代理工作流 | 多步骤任务、决策链 |
| [Multi-LLM-Provider](./Multi-LLM-Provider/) | 多模型提供商 | OpenAI、Azure、Claude、Ollama |

### 🛠️ 共享模块

| 项目 | 描述 | 功能 |
|------|------|------|
| [spring-ai-common](./spring-ai-common/) | 公共模块 | 审计日志、通用配置 |

## 🚀 快速开始

### 前置要求

- JDK 17 或更高版本
- Maven 3.6+
- OpenAI API Key（或其他 LLM 提供商的 API Key）

### 环境配置

设置 OpenAI API Key：

**Windows (CMD):**
```cmd
set OPENAI_API_KEY=your-api-key-here
```

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="your-api-key-here"
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=your-api-key-here
```

### 构建项目

在根目录执行：

```bash
mvn clean install
```

### 运行示例

每个项目都可以独立运行：

```bash
# 运行 AI 客服系统（推荐从这里开始）
cd AI-Customer-Service
mvn spring-boot:run

# 运行 RAG Demo
cd RAG-Demo
mvn spring-boot:run

# 运行其他 Demo...
```

## 📖 学习路径

### 🌟 推荐学习顺序

#### 1️⃣ 入门阶段
1. **Sample-Spring-AI** - 了解 Spring AI 基础
2. **Prompt-Engineering-Demo** - 学习如何编写好的提示词

#### 2️⃣ 进阶阶段
3. **Streaming-Demo** - 掌握流式响应
4. **Function-Calling-Demo** - 学习工具调用
5. **RAG-Demo** - 理解知识库检索

#### 3️⃣ 高级阶段
6. **Agent-Workflow-Demo** - 构建智能代理
7. **Multi-LLM-Provider** - 多模型集成
8. **AI-Customer-Service** - 完整应用实战

## 🎯 项目特点对比

| 特性 | Sample | RAG | Function | Streaming | Agent | Multi-LLM | Customer Service |
|------|--------|-----|----------|-----------|-------|-----------|------------------|
| 基础对话 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 流式响应 | ✅ | ❌ | ❌ | ✅ | ❌ | ✅ | ✅ |
| 知识库检索 | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ✅ |
| 工具调用 | ❌ | ❌ | ✅ | ❌ | ✅ | ❌ | ✅ |
| 会话管理 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| 多模型支持 | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| Web 界面 | ✅ | ❌ | ❌ | ✅ | ❌ | ✅ | ✅ |
| 生产就绪 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |

## 💡 核心功能说明

### RAG (检索增强生成)
通过向量数据库存储和检索相关文档，增强 AI 的回答准确性。适用于需要基于特定知识库回答问题的场景。

**示例项目**: RAG-Demo, AI-Customer-Service

### Function Calling (工具调用)
让 AI 能够调用外部工具和 API，实现更复杂的功能。如查询数据库、调用第三方服务等。

**示例项目**: Function-Calling-Demo, Agent-Workflow-Demo, AI-Customer-Service

### Streaming (流式响应)
实时输出 AI 的回复内容，提升用户体验，减少等待时间。

**示例项目**: Streaming-Demo, Multi-LLM-Provider, AI-Customer-Service

### Prompt Engineering (提示工程)
通过精心设计的提示词，引导 AI 生成更准确、更符合需求的回复。

**示例项目**: Prompt-Engineering-Demo, AI-Customer-Service

### Agent Workflow (智能代理)
构建能够自主决策、多步骤执行任务的智能代理。

**示例项目**: Agent-Workflow-Demo, AI-Customer-Service

## 🏗️ 项目架构

```
spring-ai-demos/
├── pom.xml                          # 父 POM
├── README.md                        # 本文件
│
├── spring-ai-common/                # 公共模块
│   ├── README.md
│   └── src/
│
├── Sample-Spring-AI/                # 基础示例
│   ├── README.md
│   └── src/
│
├── RAG-Demo/                        # RAG 示例
│   ├── README.md
│   └── src/
│
├── Function-Calling-Demo/           # Function Calling 示例
│   ├── README.md
│   └── src/
│
├── Streaming-Demo/                  # 流式响应示例
│   ├── README.md
│   └── src/
│
├── Prompt-Engineering-Demo/         # 提示工程示例
│   ├── README.md
│   └── src/
│
├── Agent-Workflow-Demo/             # 智能代理示例
│   ├── README.md
│   └── src/
│
├── Multi-LLM-Provider/              # 多模型示例
│   ├── README.md
│   └── src/
│
└── AI-Customer-Service/             # 完整应用示例
    ├── README.md
    └── src/
```

## 🎓 适用场景

### 学习场景
- Spring AI 入门学习
- AI 应用开发实践
- 企业级架构设计
- 最佳实践参考

### 实际应用
- 智能客服系统
- 知识库问答
- 文档助手
- 代码助手
- 数据分析助手

## 📦 技术栈

- **Spring Boot 3.x** - 应用框架
- **Spring AI** - AI 集成框架
- **Spring WebFlux** - 响应式编程
- **Spring Data JPA** - 数据持久化
- **H2/PostgreSQL** - 数据库
- **OpenAI/Azure/Claude/Ollama** - LLM 提供商
- **Vector Stores** - 向量数据库

## 🔧 开发工具

推荐使用以下工具：

- **IDE**: IntelliJ IDEA / Eclipse / VS Code
- **API 测试**: Postman / curl
- **数据库**: H2 Console / DBeaver
- **版本控制**: Git

## 📝 最佳实践

### 1. Prompt 设计
- 明确角色定位
- 提供清晰的指令
- 使用示例引导
- 设置输出格式

### 2. RAG 优化
- 合理的文档分块
- 高质量的向量化
- 相关性排序
- 上下文窗口管理

### 3. Function Calling
- 清晰的函数描述
- 结构化的参数定义
- 完善的错误处理
- 合理的权限控制

### 4. 性能优化
- 使用流式响应
- 缓存常用结果
- 异步处理
- 连接池管理

## 🐛 常见问题

### Q: 如何切换不同的 LLM 提供商？
A: 查看 Multi-LLM-Provider 项目，了解如何配置和切换不同的模型提供商。

### Q: 如何实现自定义的 Function？
A: 参考 Function-Calling-Demo，实现 `Function<Request, Response>` 接口即可。

### Q: 向量数据库如何选择？
A: 开发环境可使用 SimpleVectorStore（内存），生产环境推荐 PGVector 或专业向量数据库。

### Q: 如何优化响应速度？
A: 使用流式响应、减少上下文长度、使用更快的模型、添加缓存。

## 🤝 贡献指南

欢迎贡献代码、报告问题或提出建议！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 🔗 相关资源

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/)
- [OpenAI API 文档](https://platform.openai.com/docs)
- [Spring Boot 文档](https://spring.io/projects/spring-boot)

## 📧 联系方式

如有问题或建议，欢迎：
- 提交 Issue
- 发起 Discussion
- 贡献代码

---

**⭐ 如果这个项目对你有帮助，请给个 Star！**

Happy Coding! 🚀
