# Case 2.1 Spring Web

## 目录说明

本目录包含 Spring Web 相关的示例项目，涵盖模板引擎、MVC 高级功能等。

## 项目列表

| 项目 | 说明 | 主要技术 |
|------|------|----------|
| [case Spring Freemarker](./case%20Spring%20Freemarker/) | Freemarker 模板引擎示例 | Spring Boot + Freemarker |
| [case Spring Thymeleaf](./case%20Spring%20Thymeleaf/) | Thymeleaf 模板引擎示例 | Spring Boot + Thymeleaf |
| [case SpringMVC Template](./case%20SpringMVC%20Template/) | MVC 高级功能示例 | Filter, Interceptor, Session |
| [case SpringMVC AutoRestApi](./case%20SpringMVC%20AutoRestApi/) | 动态 API 注册示例 | RequestMappingHandlerMapping |

## 技术栈

- Java 11
- Spring Boot 2.7.18
- 模板引擎：Freemarker、Thymeleaf

## 模板引擎对比

| 特性 | Freemarker | Thymeleaf |
|------|------------|-----------|
| 文件后缀 | .ftl | .html |
| 浏览器预览 | 不支持 | 支持（自然模板） |
| 表达式语法 | `${var}` | `th:text="${var}"` |
| 学习曲线 | 较低 | 中等 |
| 性能 | 较高 | 中等 |
| Spring 集成 | 好 | 优秀 |

## 快速开始

每个子项目都可以独立运行：

```bash
cd "case Spring Freemarker"
mvn spring-boot:run
```

## 学习路径

1. **入门** - 先学习 Freemarker 或 Thymeleaf 基础语法
2. **进阶** - 学习 SpringMVC Template 中的 Filter、Interceptor
3. **高级** - 了解 AutoRestApi 的动态注册机制

## 相关资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Freemarker 手册](https://freemarker.apache.org/docs/)
- [Thymeleaf 教程](https://www.thymeleaf.org/documentation.html)

