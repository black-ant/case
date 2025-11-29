# Case 2.2 Webflux

## 目录说明

本目录包含 Spring WebFlux 响应式编程示例。

## 项目列表

| 项目 | 说明 |
|------|------|
| [case WebFlux Mono](./case%20WebFlux%20Mono/) | Mono/Flux 操作符示例 |
| [case WebFlux Template](./case%20WebFlux%20Template/) | 函数式端点（RouterFunction）示例 |

## 什么是 Spring WebFlux？

Spring WebFlux 是 Spring 5 引入的响应式 Web 框架，与传统的 Spring MVC 并行存在：

| 特性 | Spring MVC | Spring WebFlux |
|------|------------|----------------|
| 编程模型 | 命令式 | 响应式 |
| 线程模型 | 每请求一线程 | 事件循环 |
| 阻塞 | 支持阻塞 I/O | 非阻塞 I/O |
| 服务器 | Tomcat, Jetty | Netty, Undertow |

## 核心概念

### Reactor 类型

- **Mono<T>** - 0 或 1 个元素的异步序列
- **Flux<T>** - 0 到 N 个元素的异步序列

### 两种编程模型

1. **注解式** - 使用 @Controller, @RequestMapping（类似 Spring MVC）
2. **函数式** - 使用 RouterFunction, HandlerFunction

## 适用场景

- 高并发、低延迟的 Web 应用
- 流式数据处理
- 微服务间的响应式通信
- 需要非阻塞 I/O 的场景

## 快速开始

```bash
# 运行 Mono 示例
cd "case WebFlux Mono"
mvn spring-boot:run

# 运行 Template 示例
cd "case WebFlux Template"
mvn spring-boot:run
```

## 学习路径

1. 先学习 Reactor 基础（Mono/Flux 操作符）
2. 再学习函数式端点
3. 最后学习与其他响应式组件集成

