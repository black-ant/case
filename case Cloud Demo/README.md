# Cloud Demo

## 项目说明

Spring Cloud 微服务示例集合，演示微服务架构的各种组件和模式。

## 技术栈

- Java 11
- Spring Boot 2.7.x
- Spring Cloud 2021.x
- Nacos / Eureka / Zookeeper

## 子项目列表

### 服务注册与发现

| 项目 | 说明 |
|------|------|
| case eureka | Eureka 服务注册中心 |
| case nacos simple | Nacos 服务发现 |
| case nacos config | Nacos 配置中心 |
| case SpringCloudZookeeper | Zookeeper 服务发现 |

### 服务调用

| 项目 | 说明 |
|------|------|
| case feign | Feign 声明式调用 |
| case restTemplate | RestTemplate 调用 |

### 熔断降级

| 项目 | 说明 |
|------|------|
| case SpringCloudHystrix | Hystrix 熔断器 |
| case resilience4j | Resilience4j 熔断 |
| case Sentinel Demo | Sentinel 流控 |
| case SpringCloudCircuitBreaker | 断路器抽象 |

### 配置中心

| 项目 | 说明 |
|------|------|
| case SpringCloudConfig | Spring Cloud Config |
| case apollo | Apollo 配置中心 |

### 项目模板

| 项目 | 说明 |
|------|------|
| case CopyTemplate Eureka | Eureka 微服务模板 |
| case CopyTemplate Nacos | Nacos 微服务模板 |
| case CopyTemplate Dubbo | Dubbo 微服务模板 |

## 微服务核心概念

| 概念 | 说明 |
|------|------|
| 服务注册 | 服务启动时注册到注册中心 |
| 服务发现 | 从注册中心获取服务列表 |
| 负载均衡 | 多实例间分发请求 |
| 熔断降级 | 防止服务雪崩 |
| 配置中心 | 集中管理配置 |
| 网关 | 统一入口、路由、鉴权 |

## Eureka vs Nacos vs Zookeeper

| 特性 | Eureka | Nacos | Zookeeper |
|------|--------|-------|-----------|
| CAP | AP | AP/CP | CP |
| 配置中心 | ❌ | ✅ | ❌ |
| 健康检查 | 心跳 | 心跳+TCP | 会话 |
| 社区 | 停更 | 活跃 | 活跃 |

## 快速开始

### 1. 启动注册中心

```bash
# Nacos
docker run -d --name nacos -p 8848:8848 -e MODE=standalone nacos/nacos-server

# Eureka
cd case\ eureka/case\ server && mvn spring-boot:run
```

### 2. 启动服务

```bash
cd case\ eureka/case\ client && mvn spring-boot:run
```

## 学习资源

- [Spring Cloud 官方文档](https://spring.io/projects/spring-cloud)
- [Nacos 官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

