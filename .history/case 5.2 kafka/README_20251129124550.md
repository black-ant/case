# Kafka Demo

## 项目说明

Spring Boot Kafka 消息队列示例集合，演示 Apache Kafka 的各种使用场景。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring Kafka
- Apache Kafka

## 子项目列表

| 项目 | 说明 |
|------|------|
| case simple-demo | Kafka 基础用法 |
| kafkastep1 | Kafka 进阶配置 |
| kafkastep2 | Kafka 高级特性 |
| kafka-exception-back | Kafka 异常处理 |
| task-kafka | Kafka 定时任务 |

## 前置条件

### 安装 Kafka

**Docker Compose 方式（推荐）**：

```yaml
# docker-compose.yml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:7.0.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
```

启动：
```bash
docker-compose up -d
```

## Kafka 核心概念

| 概念 | 说明 |
|------|------|
| Topic | 消息主题（逻辑分类） |
| Partition | 分区（并行处理单元） |
| Producer | 消息生产者 |
| Consumer | 消息消费者 |
| Consumer Group | 消费者组（负载均衡） |
| Offset | 消息偏移量（消费位置） |
| Broker | Kafka 服务节点 |

## Kafka vs RabbitMQ

| 特性 | Kafka | RabbitMQ |
|------|-------|----------|
| 吞吐量 | 极高 (百万级) | 高 (万级) |
| 延迟 | 毫秒级 | 微秒级 |
| 消息持久化 | 磁盘持久化 | 内存为主 |
| 消息回溯 | 支持 | 不支持 |
| 适用场景 | 日志、大数据 | 业务消息 |

## 快速开始

### 配置连接

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
```

### 代码示例

**生产者**：
```java
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

public void send(String message) {
    kafkaTemplate.send("my-topic", message);
}
```

**消费者**：
```java
@KafkaListener(topics = "my-topic", groupId = "my-group")
public void listen(String message) {
    log.info("Received: {}", message);
}
```

## 学习资源

- [Apache Kafka 官方文档](https://kafka.apache.org/documentation/)
- [Spring Kafka 文档](https://docs.spring.io/spring-kafka/docs/current/reference/html/)

