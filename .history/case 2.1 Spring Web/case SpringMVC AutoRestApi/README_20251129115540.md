# SpringMVC AutoRestApi

## 项目说明

这是一个 Spring MVC 动态 API 注册示例，演示如何在运行时动态添加 RequestMapping。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Lombok

## 核心功能

### 动态注册 API

无需使用 `@Controller` 和 `@RequestMapping` 注解，通过编程方式动态注册 REST API。

```java
@Autowired
private RequestMappingHandlerMapping requestMappingHandlerMapping;

@PostConstruct
public void registerApi() {
    // 获取处理方法
    Method method = ReflectionUtils.findMethod(this.getClass(), "handlerMethod");
    
    // 构建映射信息
    PatternsRequestCondition patterns = new PatternsRequestCondition("/api/dynamic");
    RequestMethodsRequestCondition methods = new RequestMethodsRequestCondition(RequestMethod.GET);
    RequestMappingInfo mappingInfo = new RequestMappingInfo(patterns, methods, null, null, null, null, null);
    
    // 注册映射
    requestMappingHandlerMapping.registerMapping(mappingInfo, this, method);
}
```

## 应用场景

1. **插件化架构** - 动态加载插件模块并注册其 API
2. **配置驱动** - 根据数据库或配置文件动态生成 API
3. **API 网关** - 动态配置路由规则
4. **热更新** - 不重启应用更新 API

## 项目结构

```
src/main/java/com/gang/web/demo/
├── AutoRestApiApplication.java           # 启动类
└── config/
    ├── AutoMappingConfig.java            # 动态 API 注册配置
    ├── DefaultHandlerMapping.java        # 自定义 HandlerMapping
    ├── DefaultRequestMappingConfig.java  # RequestMapping 配置
    └── MyInterceptor.java                # 拦截器
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试动态 API

```bash
curl http://localhost:8080/test/hello/get
# 输出：Hello from dynamically registered API!
```

## 核心 API

### RequestMappingHandlerMapping

| 方法 | 说明 |
|------|------|
| `registerMapping()` | 注册新的请求映射 |
| `unregisterMapping()` | 注销已有的请求映射 |
| `getHandlerMethods()` | 获取所有已注册的处理方法 |

### RequestMappingInfo

用于描述请求映射的完整信息：

| 属性 | 说明 |
|------|------|
| PatternsRequestCondition | URL 模式 |
| RequestMethodsRequestCondition | HTTP 方法 |
| ParamsRequestCondition | 请求参数 |
| HeadersRequestCondition | 请求头 |
| ConsumesRequestCondition | Content-Type |
| ProducesRequestCondition | Accept |

## 注意事项

1. **线程安全** - registerMapping 是线程安全的
2. **Handler 实例** - 必须传入 handler 对象实例，不是 Class
3. **方法签名** - 动态注册的方法需要符合 Spring MVC 规范
4. **热更新** - 可以通过 unregisterMapping 先移除再重新注册实现更新

## 运行测试

```bash
mvn test
```

## 扩展示例

### 动态注销 API

```java
// 获取之前注册的 mappingInfo
requestMappingHandlerMapping.unregisterMapping(mappingInfo);
```

### 获取所有已注册的 API

```java
Map<RequestMappingInfo, HandlerMethod> handlerMethods = 
    requestMappingHandlerMapping.getHandlerMethods();
    
for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
    System.out.println(entry.getKey().getPatternsCondition());
}
```

