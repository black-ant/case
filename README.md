<div align="center">

# Java Case Study Collection

**综合性 Java 技术案例集合 | 从基础到高级的完整技术栈示例库**

![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

[快速开始](#-快速开始) •
[项目分类](#-项目分类) •
[更新日志](#-更新日志) •
[贡献指南](#-贡献指南)

</div>

---

## 📖 目录

- [项目简介](#-项目简介)
- [技术规范](#-技术规范)
- [快速开始](#-快速开始)
- [项目分类](#-项目分类)
  - [基础项目](#1-基础项目-case-0-1x)
  - [Web 开发](#2-web-开发-case-2x)
  - [数据库](#3-数据库-case-3x)
  - [安全认证](#4-安全认证-case-4x)
  - [消息队列](#5-消息队列-case-5x)
  - [集成服务](#6-集成服务-case-6x)
  - [任务调度](#7-任务调度-case-7x)
  - [监控分析](#8-监控分析-case-8x)
  - [前端相关](#9-前端相关-case-9x)
  - [其他模块](#其他模块)
- [项目结构](#-项目结构)
- [AI 优化记录](#-ai-优化记录)
- [注意事项](#-注意事项)
- [更新日志](#-更新日志)
- [贡献指南](#-贡献指南)
- [许可证](#-许可证)

---

## 📋 项目简介

这是一个综合性的 Java 技术案例集合，涵盖了从基础到高级的各种技术栈和框架，包括：

- **Spring 生态系统** - Spring Boot、Spring MVC、Spring Security、Spring Cloud
- **数据持久化** - JPA、MyBatis、Redis、MongoDB、Elasticsearch
- **消息中间件** - RabbitMQ、Kafka、ActiveMQ、RocketMQ
- **微服务架构** - Eureka、Nacos、Feign、Gateway、Sentinel
- **工作流引擎** - Flowable、Activiti
- **安全框架** - Spring Security、Apache Shiro、OAuth 2.0

适用于：学习参考 | 快速原型 | 技术选型 | 面试准备

---

## 🔧 技术规范

| 组件 | 版本 | 说明 |
|:-----|:-----|:-----|
| **Java** | 11 (LTS) | 长期支持版本 |
| **Spring Boot** | 2.7.18 | 2.x 最终 LTS 版本 |
| **Maven** | 3.8+ | 构建工具 |
| **JUnit** | 5.x | 单元测试框架 |

---

## 🚀 快速开始

### 环境要求

- JDK 11+
- Maven 3.8+
- Git

### 安装步骤

**1. 克隆仓库**

```bash
git clone <repository-url>
cd case
```

**2. 安装父 POM（必须首先执行）**

```bash
cd "case 0 All Parent/com-gang-case-parent"
mvn clean install -DskipTests
```

**3. 安装公共库**

```bash
cd ../common-lib
mvn clean install -DskipTests
```

**4. 运行示例项目**

```bash
cd "../../case 1.1 Base Application"
mvn spring-boot:run
```

访问 http://localhost:8080 验证运行状态。

---

## 📁 项目分类

### 1. 基础项目 (case 0-1.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 0 All Parent` | 父 POM 和公共工具库 | Maven, Hutool |
| `case 1.1 Base Application` | Spring Boot 基础应用模板 | Spring Boot |
| `case 1.1.2 Java Web No Spring` | 传统 Java Web 开发 | Servlet, Filter, Listener |
| `case 1.1.3 Java Application` | 纯 Java 控制台应用 | Core Java, JDBC |
| `case 1.2 Base Utils` | Java 工具类示例 | Java Util |

<details>
<summary>📌 推荐学习顺序</summary>

1. `case 0 All Parent` - 理解 Maven 多模块项目结构
2. `case 1.1 Base Application` - 掌握 Spring Boot 基础
3. `case 1.1.2 Java Web No Spring` - 了解 Servlet 底层原理

</details>

---

### 2. Web 开发 (case 2.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 2.1 Spring Web` | Spring MVC 模板引擎 | Freemarker, Thymeleaf |
| `case 2.2 Webflux` | 响应式 Web 开发 | WebFlux, Reactor |
| `case 2.3 easyrest` | RESTEasy JAX-RS 实现 | RESTEasy |
| `case 2.5 Jersey Web` | Jersey JAX-RS 实现 | Jersey |
| `case 2.6.1 fileUpload` | 文件上传下载 | Commons FileUpload |
| `case 2.6.5 websocket` | WebSocket 实时通信 | Spring WebSocket |
| `case 2.7.1 cxf` | Apache CXF Web Service | CXF, SOAP |

<details>
<summary>📌 技术选型建议</summary>

- **模板引擎**: 推荐 Thymeleaf（功能完整）或 Freemarker（性能优先）
- **REST 框架**: Spring MVC（默认）、Jersey（标准 JAX-RS）
- **响应式**: WebFlux 适合高并发 IO 密集型场景

</details>

---

### 3. 数据库 (case 3.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 3.1.1 JPA example` | Spring Data JPA 示例 | JPA, Hibernate |
| `case 3.2.1 Database H2` | H2 内存数据库 | H2 |
| `case 3.2.5 Database Mysql` | MySQL 数据库 | MySQL |
| `case 3.3.x redis` | Redis 缓存系列 | Spring Data Redis |
| `case 3.4.1 Mybatis` | MyBatis ORM | MyBatis |
| `case 3.4.4 mybatis-plus` | MyBatis Plus 增强 | MyBatis Plus |
| `case 3.7.1 Hikari` | HikariCP 连接池 | HikariCP |
| `case 3.7.2 Druid` | Druid 连接池 | Druid |

<details>
<summary>📌 ORM 选型对比</summary>

| 特性 | JPA/Hibernate | MyBatis | MyBatis Plus |
|:-----|:--------------|:--------|:-------------|
| 学习曲线 | 较陡 | 平缓 | 平缓 |
| SQL 控制 | 自动生成 | 手写 | 手写+增强 |
| 灵活性 | 中等 | 高 | 高 |
| 适用场景 | 简单 CRUD | 复杂 SQL | 快速开发 |

</details>

---

### 4. 安全认证 (case 4.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 4.2.1 Spring Security Demo` | Spring Security 基础 | Spring Security |
| `case 4.2.2 Spring Security Sample` | Security 进阶示例 | Spring Security |
| `case 4.3.1 Shiro Demo` | Apache Shiro 示例 | Shiro |
| `case 4.7.1 OAuth` | OAuth 2.0 授权 | OAuth 2.0 |
| `case 4.7.4 Spring SAML` | SAML 单点登录 | SAML |

---

### 5. 消息队列 (case 5.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 5.1.1 RabbitMQ` | RabbitMQ 消息队列 | RabbitMQ, AMQP |
| `case 5.2 kafka` | Apache Kafka | Kafka |
| `case 5.3 ActiveMQ` | ActiveMQ Artemis | ActiveMQ |
| `case 5.5 Spring Cloud Stream` | Spring Cloud Stream | Cloud Stream |
| `case 5.7 RocketMQ` | Apache RocketMQ | RocketMQ |

<details>
<summary>📌 消息队列选型对比</summary>

| 特性 | RabbitMQ | Kafka | RocketMQ |
|:-----|:---------|:------|:---------|
| 吞吐量 | 万级 | 百万级 | 十万级 |
| 延迟 | 微秒级 | 毫秒级 | 毫秒级 |
| 可靠性 | 高 | 高 | 高 |
| 适用场景 | 业务解耦 | 日志/大数据 | 电商/金融 |

</details>

---

### 6. 集成服务 (case 6.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 6.1.x AD` | Active Directory 集成 | LDAP |
| `case 6.2.x LDAP` | LDAP 目录服务 | Spring LDAP |
| `case 6.4.x Azure` | Azure 云服务集成 | Azure SDK |
| `case 6.5.1 SDK Work Wechat` | 企业微信 SDK | WeChat API |
| `case 6.5.2 SDK DingTalk` | 钉钉 SDK | DingTalk API |

---

### 7. 任务调度 (case 7.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 7.3 quartz` | Quartz 定时任务 | Quartz |
| `case 7.4 xxl-job` | XXL-JOB 分布式调度 | XXL-JOB |
| `case 7.9 flowable` | Flowable 工作流 | Flowable |
| `case 7.9.2 activiti` | Activiti 工作流 | Activiti |

---

### 8. 监控分析 (case 8.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 8.1.1 ELK Sample` | ELK 日志系统 | Elasticsearch, Logstash, Kibana |
| `case 8.3 grafana` | Grafana 监控 | Grafana, Prometheus |
| `case 8.4 skywalking` | SkyWalking APM | SkyWalking |
| `case 8.7 MongoDB` | MongoDB 文档数据库 | MongoDB |

---

### 9. 前端相关 (case 9.x)

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case 9.1 web baidu map` | 百度地图集成 | Baidu Map API |
| `case 9.2 VUE` | Vue.js 前端集成 | Vue.js |
| `case 9.3 selenium` | Selenium 自动化测试 | Selenium |
| `case 9.4 javafx` | JavaFX 桌面应用 | JavaFX |

---

### 其他模块

| 目录 | 说明 | 技术栈 |
|:-----|:-----|:-----|
| `case Cloud Demo` | Spring Cloud 微服务全家桶 | Eureka, Feign, Gateway |
| `case Cloud Frame` | 微服务框架模板 | Spring Cloud |
| `case Module Netty` | Netty 网络编程 | Netty |
| `case Module Thread` | 多线程与并发 | JUC |
| `case Design Pattern` | 设计模式实现 | GoF Patterns |
| `case Module Unit Test` | 单元测试最佳实践 | JUnit, Mockito |

---

## 📂 项目结构

```
case/
│
├── 📦 case 0 All Parent/           # 父 POM 和公共库
│   ├── com-gang-case-parent/       # 父 POM（依赖版本管理）
│   └── common-lib/                 # 公共工具类库
│
├── 📦 case 1.x/                    # 基础项目
├── 📦 case 2.x/                    # Web 开发
├── 📦 case 3.x/                    # 数据库
├── 📦 case 4.x/                    # 安全认证
├── 📦 case 5.x/                    # 消息队列
├── 📦 case 6.x/                    # 集成服务
├── 📦 case 7.x/                    # 任务调度
├── 📦 case 8.x/                    # 监控分析
├── 📦 case 9.x/                    # 前端相关
│
├── 📦 case Cloud Demo/             # Spring Cloud 微服务
├── 📦 case Module xxx/             # 其他功能模块
├── 📦 case Design Pattern/         # 设计模式
│
├── 📄 README.md                    # 本文档
└── 📄 plan.md                      # 优化计划（AI 辅助）
```

---

## 🤖 AI 优化记录

> 本项目于 2024 年 11 月通过 AI 辅助进行了系统性代码优化和文档完善。

### 版本升级对照表

| 组件 | 升级前版本 | 升级后版本 | 说明 |
|:-----|:----------|:----------|:-----|
| Spring Boot | 2.1.x ~ 2.5.x | **2.7.18** | 2.x 最终 LTS |
| Java | 1.8 | **11** | LTS 版本 |
| Fastjson | 1.2.x ⚠️ | **Fastjson2 2.0.43** | 安全漏洞修复 |
| Hutool | 5.1.x ~ 5.7.x | **5.8.22** | 工具库更新 |
| MyBatis Starter | 1.3.x | **2.3.2** | 兼容性更新 |
| H2 Database | 各版本 | **2.2.224** | 安全更新 |
| Flowable | 6.4.0 | **6.8.0** | 功能更新 |
| Logstash Encoder | 5.1 | **7.4** | 日志组件更新 |
| JUnit | 4.x | **5.x** | 测试框架升级 |

### 优化内容统计

| 批次 | 项目类别 | 处理数量 | 主要工作 |
|:-----|:---------|:---------|:---------|
| 第一批 | 基础项目 (case 0, 1.x) | 5 | POM 重构、文档添加 |
| 第二批 | Spring Web (case 2.1) | 4 | 模板引擎示例完善 |
| 第三批 | Web 框架 (case 2.2-2.6) | 5 | JAX-RS、WebSocket |
| 第四批 | 数据库 (case 3.x) | 5 | ORM 示例、连接池 |
| 第五批 | 消息队列 (case 5.x) | 4 | MQ 集成示例 |
| 第六批 | 任务调度 (case 7.x) | 3 | 定时任务、工作流 |
| 第七批 | 安全认证 (case 4.x) | 3 | Security 示例 |
| 第八批 | 监控工具 (case 8.x) | 3 | ELK、MongoDB |
| 第九批 | 微服务及其他 | 6 | Cloud、Netty、设计模式 |
| **合计** | - | **38+** | - |

### 主要改进项

1. **🔄 依赖升级** - 统一升级到稳定 LTS 版本，修复已知安全漏洞
2. **📝 文档完善** - 每个模块添加 README.md，核心类添加 JavaDoc 注释
3. **🐛 代码修复** - 修复语法错误，移除重复依赖，更新废弃 API
4. **🧪 测试补充** - 为非外部依赖项目添加单元测试（H2 等内存数据库）
5. **⚡ JUnit 升级** - 从 JUnit 4 迁移至 JUnit 5（Jupiter）

---

## ⚠️ 注意事项

### 外部依赖

部分项目需要预先安装和配置外部服务：

| 服务 | 相关项目 | 默认端口 |
|:-----|:---------|:---------|
| MySQL | case 3.2.5, 3.4.x | 3306 |
| Redis | case 3.3.x | 6379 |
| RabbitMQ | case 5.1.x | 5672 |
| Kafka | case 5.2 | 9092 |
| MongoDB | case 8.7 | 27017 |
| Elasticsearch | case 8.1.x | 9200 |

### 运行建议

1. **先安装父 POM** - 必须首先执行，否则子项目无法构建
2. **检查端口冲突** - 确保服务端口未被占用
3. **阅读子项目文档** - 每个项目都有独立的 README.md
4. **按顺序学习** - 建议从 case 0/1.x 开始，逐步深入

---

## 📝 更新日志

### v2.0.0 (2024-11) - AI 系统优化

#### 🔄 Changed

- 统一升级 Spring Boot 至 2.7.18 LTS
- Java 版本升级至 11
- Fastjson 升级至 Fastjson2 2.0.43（修复安全漏洞）
- JUnit 4 迁移至 JUnit 5

#### 📝 Added

- 为 38+ 个项目添加 README.md 文档
- 核心工具类添加 JavaDoc 注释
- 补充单元测试用例

#### 🐛 Fixed

- 修复代码语法错误
- 修复废弃 API 调用
- 移除重复依赖声明

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

---

## 📄 许可证

本项目采用 [MIT License](LICENSE) 开源许可证。

---

<div align="center">

**[⬆ 返回顶部](#java-case-study-collection)**

Made with ❤️ for Java developers

</div>
