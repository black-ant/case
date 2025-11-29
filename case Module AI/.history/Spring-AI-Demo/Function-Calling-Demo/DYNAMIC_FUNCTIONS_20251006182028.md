# 动态函数注册指南

这个文档详细介绍如何在运行时动态注册、管理和使用函数。

## 🎯 什么是动态函数注册？

动态函数注册允许你在应用运行时：
- ✅ 动态添加新函数
- ✅ 动态删除函数
- ✅ 查询已注册的函数
- ✅ 灵活组合函数使用

## 🚀 快速开始

### 1. 批量注册示例函数

```bash
curl -X POST http://localhost:8080/api/dynamic/register/batch
```

这会注册 5 个示例函数：
- `greetUser` - 问候用户
- `getCurrentTime` - 获取当前时间
- `getRandomNumber` - 生成随机数
- `getStringLength` - 获取字符串长度
- `toUpperCase` - 转换为大写

### 2. 使用动态函数对话

```bash
curl -X POST http://localhost:8080/api/dynamic/chat/all \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，我叫张三"
  }'
```

AI 会自动调用 `greetUser` 函数。

## 📋 API 接口详解

### 1. 注册预定义函数

```bash
curl -X POST http://localhost:8080/api/dynamic/register/example \
  -H "Content-Type: application/json" \
  -d '{
    "functionName": "myGreeting",
    "description": "向用户问候",
    "functionType": "greeting"
  }'
```


**支持的函数类型**：

| 类型 | 说明 | 参数 |
|------|------|------|
| `greeting` | 问候函数 | name（用户名） |
| `math` | 数学函数 | x, y（数字） |
| `time` | 时间函数 | timezone（时区） |
| `random` | 随机数函数 | min, max（范围） |
| `reverse` | 字符串反转 | text（文本） |

### 2. 列出所有函数

```bash
curl http://localhost:8080/api/dynamic/list
```

**响应示例**：
```json
{
  "success": true,
  "count": 5,
  "functions": {
    "greetUser": "向用户问候，需要参数 name（用户名）",
    "getCurrentTime": "获取当前时间",
    "getRandomNumber": "生成随机数，参数 min 和 max"
  }
}
```

### 3. 检查函数是否存在

```bash
curl http://localhost:8080/api/dynamic/check/greetUser
```

### 4. 注销函数

```bash
curl -X DELETE http://localhost:8080/api/dynamic/unregister/greetUser
```

### 5. 使用指定函数对话

```bash
curl -X POST http://localhost:8080/api/dynamic/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，我叫李明",
    "functionNames": ["greetUser", "getCurrentTime"]
  }'
```

### 6. 使用所有函数对话

```bash
curl -X POST http://localhost:8080/api/dynamic/chat/all \
  -H "Content-Type: application/json" \
  -d '{
    "message": "现在几点了？"
  }'
```

### 7. 清空所有函数

```bash
curl -X DELETE http://localhost:8080/api/dynamic/clear
```

## 💡 使用场景

### 场景 1：插件系统

```java
// 用户安装新插件时动态注册函数
dynamicFunctionService.registerSimpleFunction(
    "translateText",
    "翻译文本到指定语言",
    params -> {
        String text = (String) params.get("text");
        String targetLang = (String) params.get("targetLang");
        return translationService.translate(text, targetLang);
    }
);
```

### 场景 2：A/B 测试

```java
// 根据用户分组注册不同的函数
if (userGroup.equals("A")) {
    dynamicFunctionService.registerSimpleFunction(
        "recommend",
        "推荐商品（算法 A）",
        params -> algorithmA.recommend(params)
    );
} else {
    dynamicFunctionService.registerSimpleFunction(
        "recommend",
        "推荐商品（算法 B）",
        params -> algorithmB.recommend(params)
    );
}
```

### 场景 3：租户隔离

```java
// 为不同租户注册专属函数
String tenantId = getCurrentTenantId();
dynamicFunctionService.registerSimpleFunction(
    "queryData_" + tenantId,
    "查询租户数据",
    params -> tenantDataService.query(tenantId, params)
);
```

### 场景 4：临时功能

```java
// 注册临时调试函数
dynamicFunctionService.registerSimpleFunction(
    "debugInfo",
    "获取调试信息",
    params -> systemMonitor.getDebugInfo()
);

// 使用完后注销
dynamicFunctionService.unregisterFunction("debugInfo");
```

## 🔧 高级用法

### 1. 注册带类型的函数

```java
public class UserRequest {
    private String userId;
    private String action;
}

public class UserResponse {
    private String result;
    private String message;
}

// 注册类型化函数
dynamicFunctionService.registerTypedFunction(
    "handleUser",
    "处理用户操作",
    UserRequest.class,
    request -> {
        // 类型安全的处理
        return new UserResponse("success", "操作完成");
    }
);
```

### 2. 函数生命周期管理

```java
@Component
public class FunctionLifecycleManager {
    
    @Autowired
    private DynamicFunctionService dynamicFunctionService;
    
    // 应用启动时注册
    @PostConstruct
    public void registerStartupFunctions() {
        dynamicFunctionService.registerSimpleFunction(
            "systemInfo",
            "获取系统信息",
            params -> getSystemInfo()
        );
    }
    
    // 应用关闭时清理
    @PreDestroy
    public void cleanup() {
        dynamicFunctionService.clearAllFunctions();
    }
}
```

### 3. 条件注册

```java
@Configuration
public class ConditionalFunctionConfig {
    
    @Bean
    @ConditionalOnProperty(name = "features.weather.enabled", havingValue = "true")
    public void registerWeatherFunction(DynamicFunctionService service) {
        service.registerSimpleFunction(
            "getWeather",
            "获取天气信息",
            params -> weatherService.getWeather((String) params.get("city"))
        );
    }
}
```

### 4. 函数装饰器

```java
public Function<Map<String, Object>, Object> withLogging(
        Function<Map<String, Object>, Object> function) {
    return params -> {
        log.info("函数调用开始: {}", params);
        Object result = function.apply(params);
        log.info("函数调用结束: {}", result);
        return result;
    };
}

// 使用装饰器
dynamicFunctionService.registerSimpleFunction(
    "myFunction",
    "我的函数",
    withLogging(params -> {
        // 业务逻辑
        return "result";
    })
);
```

### 5. 函数组合

```java
// 注册多个相关函数
dynamicFunctionService.registerSimpleFunction(
    "fetchData",
    "获取数据",
    params -> dataService.fetch(params)
);

dynamicFunctionService.registerSimpleFunction(
    "processData",
    "处理数据",
    params -> dataService.process(params)
);

dynamicFunctionService.registerSimpleFunction(
    "saveData",
    "保存数据",
    params -> dataService.save(params)
);

// AI 可以链式调用这些函数完成复杂任务
```

## 🎨 完整示例

### 示例 1：动态注册问候函数

```bash
# 1. 注册函数
curl -X POST http://localhost:8080/api/dynamic/register/example \
  -H "Content-Type: application/json" \
  -d '{
    "functionName": "greetUser",
    "description": "向用户问候",
    "functionType": "greeting"
  }'

# 2. 使用函数
curl -X POST http://localhost:8080/api/dynamic/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，我叫张三",
    "functionNames": ["greetUser"]
  }'

# 3. 注销函数
curl -X DELETE http://localhost:8080/api/dynamic/unregister/greetUser
```

### 示例 2：批量注册并使用

```bash
# 1. 批量注册
curl -X POST http://localhost:8080/api/dynamic/register/batch

# 2. 查看已注册的函数
curl http://localhost:8080/api/dynamic/list

# 3. 使用所有函数
curl -X POST http://localhost:8080/api/dynamic/chat/all \
  -H "Content-Type: application/json" \
  -d '{
    "message": "现在几点了？帮我生成一个 1 到 100 的随机数"
  }'
```

### 示例 3：动态切换函数

```bash
# 1. 注册版本 A
curl -X POST http://localhost:8080/api/dynamic/register/example \
  -H "Content-Type: application/json" \
  -d '{
    "functionName": "recommend",
    "description": "推荐算法 A",
    "functionType": "math"
  }'

# 2. 使用
curl -X POST http://localhost:8080/api/dynamic/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "给我推荐一些商品",
    "functionNames": ["recommend"]
  }'

# 3. 切换到版本 B
curl -X DELETE http://localhost:8080/api/dynamic/unregister/recommend

curl -X POST http://localhost:8080/api/dynamic/register/example \
  -H "Content-Type: application/json" \
  -d '{
    "functionName": "recommend",
    "description": "推荐算法 B",
    "functionType": "random"
  }'
```

## 🔐 安全考虑

### 1. 权限控制

```java
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/register/example")
public Map<String, Object> registerExampleFunction(...) {
    // 只有管理员可以注册函数
}
```

### 2. 函数白名单

```java
private static final Set<String> ALLOWED_FUNCTION_TYPES = 
    Set.of("greeting", "math", "time", "random");

if (!ALLOWED_FUNCTION_TYPES.contains(request.getFunctionType())) {
    throw new IllegalArgumentException("不允许的函数类型");
}
```

### 3. 参数验证

```java
dynamicFunctionService.registerSimpleFunction(
    "safeFunction",
    "安全函数",
    params -> {
        // 验证参数
        if (!isValidInput(params)) {
            throw new IllegalArgumentException("无效的输入");
        }
        return processData(params);
    }
);
```

## 📊 监控和调试

### 1. 函数调用统计

```java
@Aspect
@Component
public class FunctionCallMonitor {
    
    private final Map<String, AtomicInteger> callCounts = new ConcurrentHashMap<>();
    
    @Around("execution(* com.example.functioncalling.service.DynamicFunctionService.chatWith*(..))")
    public Object monitor(ProceedingJoinPoint joinPoint) throws Throwable {
        String functionName = extractFunctionName(joinPoint);
        callCounts.computeIfAbsent(functionName, k -> new AtomicInteger()).incrementAndGet();
        return joinPoint.proceed();
    }
}
```

### 2. 性能监控

```java
dynamicFunctionService.registerSimpleFunction(
    "monitoredFunction",
    "带监控的函数",
    params -> {
        long start = System.currentTimeMillis();
        try {
            return businessLogic(params);
        } finally {
            long duration = System.currentTimeMillis() - start;
            metrics.record("function.duration", duration);
        }
    }
);
```

## 🎓 最佳实践

1. **命名规范**：使用清晰的函数名，如 `getUserInfo`、`calculatePrice`
2. **描述详细**：提供详细的函数描述，帮助 AI 理解用途
3. **参数验证**：始终验证输入参数
4. **错误处理**：优雅地处理异常情况
5. **资源清理**：及时注销不再使用的函数
6. **版本管理**：为函数添加版本号，如 `recommend_v1`、`recommend_v2`
7. **文档维护**：记录每个动态函数的用途和参数

## 🔗 参考资料

- [Spring AI Function Calling](https://docs.spring.io/spring-ai/reference/api/functions.html)
- [FunctionCallback API](https://docs.spring.io/spring-ai/reference/api/functioncallback.html)
