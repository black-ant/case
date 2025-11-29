# Function Calling Demo - 工具调用示例

这个项目展示了如何使用 Spring AI 的 Function Calling 功能，让 AI 能够调用外部工具和 API，实现更复杂的功能。

## 🎯 功能特性

- ✅ 声明式函数注册
- ✅ 自动参数解析
- ✅ 多函数并行调用
- ✅ 函数链式调用
- ✅ 错误处理
- ✅ 函数描述和文档

## 🏗️ 技术架构

### 核心概念

**Function Calling** 允许 AI 模型：
1. 识别用户意图
2. 选择合适的工具
3. 提取参数
4. 调用函数
5. 处理结果
6. 生成回复

## 📦 依赖项

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

## 🚀 快速开始

### 1. 定义函数

```java
@Component
public class WeatherFunction implements Function<WeatherRequest, WeatherResponse> {
    
    @Override
    public WeatherResponse apply(WeatherRequest request) {
        // 调用天气 API
        return new WeatherResponse(
            request.location(),
            "晴天",
            25.0
        );
    }
    
    public record WeatherRequest(
        @JsonProperty(required = true) String location
    ) {}
    
    public record WeatherResponse(
        String location,
        String condition,
        Double temperature
    ) {}
}
```

### 2. 注册函数

```java
@Configuration
public class FunctionConfig {
    
    @Bean
    @Description("获取指定城市的天气信息")
    public WeatherFunction weatherFunction() {
        return new WeatherFunction();
    }
}
```

### 3. 使用函数

```java
@Service
public class ChatService {
    
    private final ChatClient chatClient;
    
    public ChatService(ChatClient.Builder builder, WeatherFunction weatherFunction) {
        this.chatClient = builder
            .defaultFunctions(weatherFunction)
            .build();
    }
    
    public String chat(String message) {
        return chatClient.prompt()
            .user(message)
            .call()
            .content();
    }
}
```

### 4. 测试

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "北京今天天气怎么样？"}'
```

## 💡 核心示例

### 示例 1: 简单函数调用

```java
@Component
public class CalculatorFunction implements Function<CalculatorRequest, CalculatorResponse> {
    
    @Override
    public CalculatorResponse apply(CalculatorRequest request) {
        double result = switch (request.operation()) {
            case "add" -> request.a() + request.b();
            case "subtract" -> request.a() - request.b();
            case "multiply" -> request.a() * request.b();
            case "divide" -> request.a() / request.b();
            default -> throw new IllegalArgumentException("不支持的操作");
        };
        return new CalculatorResponse(result);
    }
    
    public record CalculatorRequest(
        @JsonProperty(required = true) double a,
        @JsonProperty(required = true) double b,
        @JsonProperty(required = true) String operation
    ) {}
    
    public record CalculatorResponse(double result) {}
}
```

### 示例 2: 数据库查询函数

```java
@Component
@RequiredArgsConstructor
public class UserQueryFunction implements Function<UserQueryRequest, UserQueryResponse> {
    
    private final UserRepository userRepository;
    
    @Override
    public UserQueryResponse apply(UserQueryRequest request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        return new UserQueryResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
    
    public record UserQueryRequest(
        @JsonProperty(required = true) Long userId
    ) {}
    
    public record UserQueryResponse(
        Long id,
        String name,
        String email
    ) {}
}
```

### 示例 3: 多函数组合

```java
@Service
public class MultiToolService {
    
    private final ChatClient chatClient;
    
    public MultiToolService(
        ChatClient.Builder builder,
        WeatherFunction weatherFunction,
        CalculatorFunction calculatorFunction,
        UserQueryFunction userQueryFunction
    ) {
        this.chatClient = builder
            .defaultFunctions(
                weatherFunction,
                calculatorFunction,
                userQueryFunction
            )
            .build();
    }
    
    public String chat(String message) {
        // AI 会自动选择合适的函数调用
        return chatClient.prompt()
            .user(message)
            .call()
            .content();
    }
}
```

## 🔧 配置说明

### application.yml

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7
          # 启用函数调用
          function-callbacks: true
```

## 🎓 最佳实践

### 1. 函数设计

**清晰的函数描述**
```java
@Bean
@Description("查询指定城市的实时天气信息，包括温度、湿度、天气状况等")
public WeatherFunction weatherFunction() {
    return new WeatherFunction();
}
```

**结构化的参数**
```java
public record WeatherRequest(
    @JsonProperty(value = "location", required = true)
    @JsonPropertyDescription("城市名称，如：北京、上海")
    String location,
    
    @JsonProperty(value = "unit", required = false, defaultValue = "celsius")
    @JsonPropertyDescription("温度单位：celsius（摄氏度）或 fahrenheit（华氏度）")
    String unit
) {}
```

### 2. 错误处理

```java
@Override
public WeatherResponse apply(WeatherRequest request) {
    try {
        // 调用外部 API
        return weatherApi.getWeather(request.location());
    } catch (Exception e) {
        log.error("获取天气失败", e);
        return new WeatherResponse(
            request.location(),
            "无法获取天气信息",
            null
        );
    }
}
```

### 3. 参数验证

```java
@Override
public CalculatorResponse apply(CalculatorRequest request) {
    if (request.operation().equals("divide") && request.b() == 0) {
        throw new IllegalArgumentException("除数不能为零");
    }
    // 执行计算
}
```

### 4. 性能优化

```java
@Component
public class CachedWeatherFunction implements Function<WeatherRequest, WeatherResponse> {
    
    @Cacheable(value = "weather", key = "#request.location()")
    @Override
    public WeatherResponse apply(WeatherRequest request) {
        // 结果会被缓存
        return weatherApi.getWeather(request.location());
    }
}
```

## 📊 适用场景

- 🔍 **数据查询**: 查询数据库、API
- 🛠️ **工具集成**: 调用第三方服务
- 📊 **数据分析**: 执行计算、统计
- 🎯 **任务执行**: 发送邮件、创建工单
- 🔄 **流程自动化**: 多步骤任务编排

## 🔍 进阶功能

### 异步函数调用

```java
@Component
public class AsyncEmailFunction implements Function<EmailRequest, EmailResponse> {
    
    @Async
    @Override
    public EmailResponse apply(EmailRequest request) {
        // 异步发送邮件
        emailService.sendAsync(request.to(), request.subject(), request.body());
        return new EmailResponse(true, "邮件已发送");
    }
}
```

### 函数链式调用

```java
// AI 可以自动进行多步骤调用
// 1. 查询用户信息
// 2. 根据用户位置查询天气
// 3. 发送天气通知
```

### 条件函数调用

```java
@Bean
@Description("仅在工作时间可用的客服函数")
public CustomerServiceFunction customerServiceFunction() {
    return new CustomerServiceFunction() {
        @Override
        public Response apply(Request request) {
            if (!isWorkingHours()) {
                return new Response("当前非工作时间，请稍后再试");
            }
            return processRequest(request);
        }
    };
}
```

## 🐛 常见问题

**Q: 函数什么时候会被调用？**
A: 当 AI 判断需要使用该函数来回答用户问题时，会自动调用。

**Q: 如何控制函数调用？**
A: 通过函数描述、参数定义和提示词来引导 AI 的选择。

**Q: 函数调用失败怎么办？**
A: 实现完善的错误处理，返回友好的错误信息给 AI。

**Q: 支持多少个函数？**
A: 理论上无限制，但建议控制在 10-20 个以内，避免 AI 选择困难。

## 📚 相关资源

- [Spring AI Function Calling 文档](https://docs.spring.io/spring-ai/reference/api/functions.html)
- [OpenAI Function Calling 指南](https://platform.openai.com/docs/guides/function-calling)

## 🔗 相关项目

- [AI-Customer-Service](../AI-Customer-Service/) - 集成了多个函数的完整应用
- [Agent-Workflow-Demo](../Agent-Workflow-Demo/) - 使用函数构建智能代理

---

[返回主页](../README.md)
