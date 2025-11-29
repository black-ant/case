# Jersey Web Demo

## 项目说明

Spring Boot + Jersey (JAX-RS 参考实现) 示例，演示如何使用 Jersey 开发 REST API。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Jersey 2.x (Spring Boot 内置)

## 什么是 Jersey？

Jersey 是 JAX-RS (Java API for RESTful Web Services) 的参考实现：

- **标准实现** - Oracle/Eclipse 基金会维护
- **功能完整** - 支持所有 JAX-RS 特性
- **Spring 集成** - 与 Spring Boot 无缝集成

## 项目结构

```
src/main/java/com/gang/study/jersey/comgangjersey/
├── ComGangJerseyApplication.java   # 启动类
├── config/
│   └── JerseyConfig.java           # Jersey 配置
└── controller/
    └── StartController.java        # 资源类
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
# 健康检查（注意路径前缀 /rest）
curl http://localhost:8080/rest/start/health

# 获取城市信息
curl http://localhost:8080/rest/start/city

# 获取指定ID信息
curl http://localhost:8080/rest/start/info/123
```

## Jersey 配置

### ResourceConfig

```java
@Component
@ApplicationPath("/rest")  // 所有 Jersey 接口的前缀
public class JerseyConfig extends ResourceConfig {
    
    public JerseyConfig() {
        // 扫描包
        packages("com.gang.controller");
        
        // 注册单个类
        register(StartController.class);
        
        // 注册过滤器
        register(LoggingFilter.class);
    }
}
```

### 资源类

```java
@Component
@Path("/users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        User created = userService.save(user);
        return Response.created(URI.create("/users/" + created.getId()))
                       .entity(created)
                       .build();
    }
}
```

## JAX-RS 注解速查

| 注解 | 说明 |
|------|------|
| @Path | 资源路径 |
| @GET, @POST, @PUT, @DELETE | HTTP 方法 |
| @Produces | 响应内容类型 |
| @Consumes | 请求内容类型 |
| @PathParam | 路径参数 |
| @QueryParam | 查询参数 |
| @HeaderParam | 请求头参数 |
| @FormParam | 表单参数 |

## 运行测试

```bash
mvn test
```

## 注意事项

- Jersey 默认路径由 @ApplicationPath 定义（本例为 /rest）
- 资源类需要添加 @Component 注解才能使用 Spring DI
- Jersey 和 Spring MVC 可以共存，使用不同的路径前缀

## 学习资源

- [Jersey 官方文档](https://eclipse-ee4j.github.io/jersey/)
- [Spring Boot Jersey 指南](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.jersey)

