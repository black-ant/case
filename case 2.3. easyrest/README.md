# RESTEasy Demo

## 项目说明

Spring Boot + RESTEasy (JAX-RS) 集成示例，演示如何使用 JAX-RS 标准开发 REST API。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- RESTEasy 4.7.x
- JAX-RS 2.1

## 什么是 JAX-RS？

JAX-RS (Java API for RESTful Web Services) 是 Java EE 的 REST API 标准规范，主要实现包括：

- **Jersey** - 参考实现
- **RESTEasy** - JBoss/Red Hat 实现
- **Apache CXF** - Apache 实现

## JAX-RS 核心注解

| 注解 | 说明 | Spring MVC 对应 |
|------|------|-----------------|
| @Path | 资源路径 | @RequestMapping |
| @GET | GET 方法 | @GetMapping |
| @POST | POST 方法 | @PostMapping |
| @PUT | PUT 方法 | @PutMapping |
| @DELETE | DELETE 方法 | @DeleteMapping |
| @Produces | 响应类型 | produces |
| @Consumes | 请求类型 | consumes |
| @PathParam | 路径参数 | @PathVariable |
| @QueryParam | 查询参数 | @RequestParam |

## 项目结构

```
src/main/java/com/gamg/easyrest/demo/
├── DemoApplication.java          # 启动类
├── config/
│   └── RestEasyConfig.java       # RESTEasy 配置
├── controller/
│   ├── TestAPI.java              # API 接口
│   ├── TestController.java       # 测试资源
│   └── UserController.java       # 用户资源
├── entity/
│   ├── RESTHeaders.java          # 自定义请求头
│   └── User.java                 # 用户实体
└── service/
    └── TestAPIService.java       # API 服务
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
# 测试接口
curl http://localhost:8080/other/test

# 用户接口（如果有）
curl http://localhost:8080/api/users
```

## 代码示例

### 基础资源类

```java
@Path("/users")
@Component
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        User created = userService.save(user);
        return Response.created(URI.create("/users/" + created.getId()))
                       .entity(created)
                       .build();
    }
}
```

## 运行测试

```bash
mvn test
```

## 注意事项

- RESTEasy 与 Spring MVC 可以共存，但需要配置不同的路径前缀
- JAX-RS 资源类需要添加 @Component 注解才能被 Spring 管理
- 建议使用 @Produces/@Consumes 明确指定媒体类型

## 学习资源

- [JAX-RS 规范](https://jakarta.ee/specifications/restful-ws/)
- [RESTEasy 文档](https://resteasy.dev/)

