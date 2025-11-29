# Base Application - Spring Boot 基础应用示例

## 项目说明

这是一个 Spring Boot 基础应用示例，演示 RESTful API 的基本用法。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Fastjson2 2.0.43
- Lombok

## 功能演示

### 1. 基础接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/test` | GET | 健康检查接口 |
| `/callBack` | POST | 回调接口示例 |

### 2. 用户接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/user/get/test` | GET | 测试接口 |
| `/user/get/path/{key}` | GET | 路径参数示例 |
| `/user/get/param?key=xxx` | GET | 查询参数示例 |
| `/user/post/test?key=xxx` | POST | POST 查询参数示例 |
| `/user/post/create` | POST | 创建用户（JSON 请求体） |

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
# 健康检查
curl http://localhost:8080/test

# 路径参数
curl http://localhost:8080/user/get/path/hello

# 查询参数
curl http://localhost:8080/user/get/param?key=world

# 创建用户
curl -X POST http://localhost:8080/user/post/create \
  -H "Content-Type: application/json" \
  -d '{"username":"张三","age":25}'
```

## 项目结构

```
src/main/java/com/gang/base/
├── BaseApplication.java      # 启动类
├── common/
│   └── User.java             # 用户实体
└── controller/
    ├── BaseController.java   # 基础控制器
    └── UserController.java   # 用户控制器
```

## 学习要点

1. **@RestController** - REST 控制器注解，等同于 @Controller + @ResponseBody
2. **@GetMapping / @PostMapping** - HTTP 方法映射
3. **@PathVariable** - 路径参数绑定
4. **@RequestParam** - 查询参数绑定
5. **@RequestBody** - 请求体 JSON 自动反序列化

## 运行测试

```bash
mvn test
```

