# WebFlux Template Demo

## 项目说明

Spring WebFlux 函数式端点（RouterFunction）示例，演示函数式编程模型。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring WebFlux

## 函数式 vs 注解式

| 特性 | 注解式 | 函数式 |
|------|--------|--------|
| 路由定义 | @RequestMapping | RouterFunction |
| 处理器 | @Controller 方法 | HandlerFunction |
| 风格 | 声明式 | 函数式 |
| 可测试性 | 需要 MockMvc | 纯函数测试 |

## 核心组件

### 1. RouterFunction

路由函数，定义请求到处理器的映射：

```java
@Bean
public RouterFunction<ServerResponse> route(GreetingHandler handler) {
    return RouterFunctions
            .route(GET("/hello").and(accept(TEXT_PLAIN)), handler::hello)
            .andRoute(GET("/user/{id}"), handler::getUser)
            .andRoute(POST("/user"), handler::createUser);
}
```

### 2. HandlerFunction

处理函数，接收请求返回响应：

```java
public Mono<ServerResponse> hello(ServerRequest request) {
    return ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue("Hello, World!");
}
```

### 3. RequestPredicates

请求匹配条件：

```java
GET("/path")                    // GET 请求
POST("/path")                   // POST 请求
path("/api/**")                 // 路径匹配
accept(MediaType.APPLICATION_JSON)  // Accept 头
contentType(MediaType.APPLICATION_JSON)  // Content-Type
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
curl http://localhost:8080/hello
# 输出：Hello, Spring WebFlux!
```

## 运行测试

```bash
mvn test
```

## 项目结构

```
src/main/java/com/gang/spring/webflux/demo/
├── DemoApplication.java           # 启动类
├── config/
│   └── GreetingRouter.java        # 路由配置
└── service/
    ├── GreetingHandler.java       # 请求处理器
    └── GreetingWebClient.java     # WebClient 客户端
```

## 扩展示例

### 复杂路由

```java
RouterFunctions
    .route(GET("/users"), handler::listUsers)
    .andRoute(GET("/users/{id}"), handler::getUser)
    .andRoute(POST("/users"), handler::createUser)
    .andRoute(PUT("/users/{id}"), handler::updateUser)
    .andRoute(DELETE("/users/{id}"), handler::deleteUser)
    .filter((request, next) -> {
        // 过滤器逻辑
        return next.handle(request);
    });
```

### 嵌套路由

```java
RouterFunctions
    .nest(path("/api/v1"),
        RouterFunctions
            .route(GET("/users"), handler::listUsers)
            .andRoute(GET("/orders"), handler::listOrders)
    );
```

