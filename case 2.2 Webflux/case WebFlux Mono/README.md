# WebFlux Mono Demo

## 项目说明

Spring WebFlux Mono/Flux 响应式编程示例，演示 Project Reactor 的核心操作符。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring WebFlux
- Project Reactor

## 什么是响应式编程？

响应式编程是一种面向数据流和变化传播的编程范式，具有以下特点：

- **非阻塞** - 不会阻塞线程等待 I/O
- **背压支持** - 消费者可以控制生产者速度
- **声明式** - 描述"做什么"而不是"怎么做"

## Mono vs Flux

| 类型 | 元素数量 | 适用场景 |
|------|----------|----------|
| Mono | 0 或 1 | 单个结果，如数据库查询单条记录 |
| Flux | 0 到 N | 多个结果，如列表、流数据 |

## 演示内容

### 1. 数据转换操作符

```java
// map - 同步转换
Mono.just("hello")
    .map(s -> s.toUpperCase())
    .subscribe(System.out::println);  // HELLO

// flatMap - 异步转换
Mono.just("hello")
    .flatMap(s -> Mono.just(s.toUpperCase()))
    .subscribe(System.out::println);  // HELLO

// transform - 变换整个流
Mono.just("hello")
    .transform(mono -> mono.map(String::toUpperCase))
    .subscribe(System.out::println);  // HELLO
```

### 2. 过滤操作符

```java
Mono.just(5)
    .filter(n -> n > 3)
    .subscribe(System.out::println);  // 5

Mono.just(2)
    .filter(n -> n > 3)
    .defaultIfEmpty(-1)
    .subscribe(System.out::println);  // -1
```

### 3. 生命周期回调

```java
Mono.just("data")
    .doOnNext(s -> log.info("Next: {}", s))
    .doOnSuccess(s -> log.info("Success: {}", s))
    .doFinally(signal -> log.info("Finally: {}", signal))
    .subscribe(System.out::println);
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

应用启动后会自动执行各种 Mono 操作符演示。

## 运行测试

```bash
mvn test
```

## 常用操作符速查

| 操作符 | 说明 |
|--------|------|
| `map` | 同步转换元素 |
| `flatMap` | 异步转换元素 |
| `filter` | 过滤元素 |
| `defaultIfEmpty` | 空值默认值 |
| `switchIfEmpty` | 空值切换到另一个流 |
| `doOnNext` | 元素发出时回调 |
| `doOnError` | 错误时回调 |
| `doFinally` | 终止时回调 |
| `log` | 记录所有信号 |
| `block` | 阻塞获取结果（慎用） |

## 学习资源

- [Project Reactor 官方文档](https://projectreactor.io/docs)
- [Spring WebFlux 参考文档](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

