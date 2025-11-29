# ActiveMQ Demo

## 项目说明

Spring Boot ActiveMQ Artemis 消息队列示例。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring JMS
- ActiveMQ Artemis

## ActiveMQ vs Artemis

| 特性 | ActiveMQ Classic | ActiveMQ Artemis |
|------|------------------|------------------|
| 性能 | 一般 | 高性能 |
| 协议 | AMQP, STOMP | AMQP, STOMP, MQTT |
| 集群 | 支持 | 更好支持 |
| 推荐 | 遗留系统 | 新项目 |

## 项目结构

```
src/main/java/com/gang/study/activemq/demo/
├── DemoApplication.java          # 启动类
└── service/
    ├── ActiveMQController.java   # 测试控制器
    ├── Producer.java             # 消息生产者
    └── Consumer.java             # 消息消费者
```

## 快速开始

### 安装 Artemis

**Docker 方式**：
```bash
docker run -d --name artemis -p 61616:61616 -p 8161:8161 vromero/activemq-artemis
```

### 配置连接

```properties
spring.artemis.mode=native
spring.artemis.host=localhost
spring.artemis.port=61616
spring.artemis.user=artemis
spring.artemis.password=artemis
```

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

**生产者**：
```java
@Autowired
private JmsTemplate jmsTemplate;

public void send(String message) {
    jmsTemplate.convertAndSend("my-queue", message);
}
```

**消费者**：
```java
@JmsListener(destination = "my-queue")
public void receive(String message) {
    log.info("Received: {}", message);
}
```

## 学习资源

- [ActiveMQ Artemis 官方文档](https://activemq.apache.org/components/artemis/documentation/)
- [Spring JMS 文档](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#jms)

