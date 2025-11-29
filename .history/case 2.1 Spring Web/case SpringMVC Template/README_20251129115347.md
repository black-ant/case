# SpringMVC Template

## 项目说明

这是一个 Spring MVC 高级功能示例项目，演示 Filter、Interceptor、Session、Cookie、异常处理等特性。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Fastjson2
- Lombok

## 功能演示

### 1. Filter 过滤器

请求日志过滤器，记录每个请求的 URL 和处理时间。

```java
@Component
public class CustomerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException {
        // 前置处理
        chain.doFilter(request, response);
        // 后置处理
    }
}
```

### 2. Interceptor 拦截器

Spring MVC 拦截器，可访问 Handler 信息。

### 3. Session 管理

Session 的创建、读取和销毁。

### 4. Cookie 操作

Cookie 的设置和读取。

### 5. 异常处理

全局异常处理器 @ControllerAdvice。

## 项目结构

```
src/main/java/com/gang/web/demo/
├── DemoApplication.java          # 启动类
├── config/                       # 配置类
│   ├── AppProperties.java        # 应用属性配置
│   ├── AsyncListenerConfig.java  # 异步监听配置
│   ├── DatabaseConfig.java       # 数据库配置
│   ├── ErrorConfig.java          # 错误处理配置
│   └── WebCoustomerConfig.java   # Web 自定义配置
├── controller/                   # 控制器
│   ├── BeanController.java       # Bean 操作示例
│   ├── CookieController.java     # Cookie 操作示例
│   ├── ExceptionController.java  # 异常处理示例
│   ├── SessionController.java    # Session 操作示例
│   └── TestController.java       # 测试控制器
├── filter/                       # 过滤器
│   └── CustomerFilter.java       # 自定义过滤器
├── interceptor/                  # 拦截器
│   ├── ComponentInterceptor.java # 组件拦截器
│   └── ConfigInterceptor.java    # 配置拦截器
├── listener/                     # 监听器
│   └── DefaultHttpSessionListener.java  # Session 监听器
├── service/                      # 服务层
│   └── UserService.java          # 用户服务
└── to/                           # 传输对象
    ├── DateBeanTO.java
    ├── InnerTO.java
    ├── ModuleTO.java
    └── TestDemo.java
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
# 简单 GET 请求
curl http://localhost:8080/test/get

# 带参数的 GET 请求
curl "http://localhost:8080/test/getParam/key1?name=value1"

# POST JSON 请求
curl -X POST http://localhost:8080/test/getBody \
  -H "Content-Type: application/json" \
  -d '{"name":"test","description":"desc"}'
```

## Filter vs Interceptor

| 特性 | Filter | Interceptor |
|------|--------|-------------|
| 规范 | Servlet 规范 | Spring MVC |
| 执行时机 | DispatcherServlet 之前 | DispatcherServlet 之后 |
| 可获取信息 | 原始请求/响应 | Handler、ModelAndView |
| 依赖注入 | 需要特殊处理 | 原生支持 |
| 典型用途 | 编码、CORS、日志 | 权限、审计、性能 |

## 请求处理流程

```
Client Request
     │
     ▼
  Filter 1 ──► Filter 2 ──► ... ──► Filter N
     │
     ▼
DispatcherServlet
     │
     ▼
Interceptor.preHandle()
     │
     ▼
  Controller
     │
     ▼
Interceptor.postHandle()
     │
     ▼
View Rendering
     │
     ▼
Interceptor.afterCompletion()
     │
     ▼
Client Response
```

## 运行测试

```bash
mvn test
```

## 学习要点

1. **@ServletComponentScan** - 扫描 Servlet 3.0 注解
2. **Filter 链** - 多个 Filter 按顺序执行
3. **Interceptor** - 可获取 Handler 和 ModelAndView
4. **Session** - HttpSession 会话管理
5. **@ControllerAdvice** - 全局异常处理

