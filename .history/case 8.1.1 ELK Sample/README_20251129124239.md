# ELK Sample

## 项目说明

Spring Boot ELK (Elasticsearch + Logstash + Kibana) 日志集成示例。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Logback
- Logstash Logback Encoder

## ELK 简介

| 组件 | 说明 |
|------|------|
| Elasticsearch | 分布式搜索和分析引擎 |
| Logstash | 日志收集和处理管道 |
| Kibana | 可视化和分析平台 |

## 架构

```
Application → Logstash → Elasticsearch → Kibana
     ↓
  logback.xml
```

## 快速开始

### 启动 ELK

```bash
# Docker Compose
docker-compose up -d elasticsearch logstash kibana
```

### 配置 Logback

`logback.xml`:
```xml
<appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
    <destination>localhost:5000</destination>
    <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
</appender>
```

### 启动应用

```bash
mvn spring-boot:run
```

### 访问 Kibana

http://localhost:5601

## 日志格式

JSON 格式日志示例：
```json
{
  "@timestamp": "2024-01-01T12:00:00.000Z",
  "@version": "1",
  "message": "Request received",
  "logger_name": "com.gang.study.elk",
  "level": "INFO",
  "application": "elk-sample"
}
```

## 学习资源

- [Elastic Stack 官方文档](https://www.elastic.co/guide/index.html)
- [Logstash Logback Encoder](https://github.com/logfellow/logstash-logback-encoder)

