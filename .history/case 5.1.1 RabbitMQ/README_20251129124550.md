# RabbitMQ Demo

## 项目说明

Spring Boot RabbitMQ 消息队列示例，演示 AMQP 协议的使用。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring AMQP
- RabbitMQ

## 前置条件

### 安装 RabbitMQ

**Docker 方式（推荐）**：
```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

**访问管理界面**：http://localhost:15672 (guest/guest)

## 核心概念

| 概念 | 说明 |
|------|------|
| Producer | 消息生产者 |
| Consumer | 消息消费者 |
| Queue | 消息队列 |
| Exchange | 交换机（消息路由） |
| Binding | 绑定关系 |
| Routing Key | 路由键 |

## 交换机类型

| 类型 | 说明 | 使用场景 |
|------|------|----------|
| Direct | 精确匹配路由键 | 点对点通信 |
| Topic | 模式匹配路由键 | 主题订阅 |
| Fanout | 广播到所有队列 | 广播通知 |
| Headers | 匹配消息头 | 复杂路由 |

## 项目结构

```
src/main/java/com/mqproject/demo/
├── DemoApplication.java          # 启动类
├── config/
│   └── RabbitConfig.java         # RabbitMQ 配置
├── controller/
│   └── StartController.java      # 测试控制器
├── entity/
│   └── Book.java                 # 消息实体
└── handler/
    ├── MQProduce.java            # 消息生产者
    ├── MQCustomer.java           # 消息消费者
    └── BookHandler.java          # 业务处理器
```

## 快速开始

### 配置连接

编辑 `application.properties`：

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

### 配置交换机和队列

```java
@Configuration
public class RabbitConfig {
    
    @Bean
    public Queue bookQueue() {
        return new Queue("book.queue", true);
    }
    
    @Bean
    public DirectExchange bookExchange() {
        return new DirectExchange("book.exchange");
    }
    
    @Bean
    public Binding bookBinding() {
        return BindingBuilder.bind(bookQueue())
                .to(bookExchange())
                .with("book.routing.key");
    }
}
```

### 发送消息

```java
@Service
public class MQProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void send(Book book) {
        rabbitTemplate.convertAndSend("book.exchange", "book.routing.key", book);
    }
}
```

### 接收消息

```java
@Component
public class MQConsumer {
    
    @RabbitListener(queues = "book.queue")
    public void receive(Book book) {
        log.info("Received: {}", book);
    }
}
```

## 学习资源

- [RabbitMQ 官方文档](https://www.rabbitmq.com/documentation.html)
- [Spring AMQP 文档](https://docs.spring.io/spring-amqp/docs/current/reference/html/)

