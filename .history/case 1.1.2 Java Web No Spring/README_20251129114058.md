# Java Web No Spring - 传统 Java Web 项目示例

## 项目说明

这是一个不使用 Spring 框架的传统 Java Web 项目示例，演示原生 Servlet/Filter/Listener 的用法。

## 技术栈

- Java 11
- Servlet 4.0
- JSP 2.3
- JDBC (MySQL)
- SLF4J + Log4j2

## 项目结构

```
src/main/java/com/gang/study/web/
├── StartApplication.java        # 应用入口标识
├── servlet/
│   ├── StartServlet.java        # Servlet 示例
│   └── ServerServlet.java       # 服务 Servlet
├── filter/
│   └── MyFilter.java            # 过滤器示例
├── listener/
│   ├── MyServletContextListener.java  # 上下文监听器
│   └── WebContextListener.java        # Web 监听器
├── controller/
│   └── StartController.java     # 控制器
├── service/
│   └── StartService.java        # 服务层
├── dao/
│   └── JDBCTemplate.java        # JDBC 模板
├── bean/
│   └── BootYaml.java            # YAML 配置 Bean
├── to/
│   └── UserTO.java              # 传输对象
└── utils/
    ├── ResourceUtils.java       # 资源工具
    └── YmlPropUtils.java        # YAML 属性工具
```

## 核心概念

### 1. Servlet

Servlet 是 Java Web 的核心组件，用于处理 HTTP 请求：

```java
@WebServlet(name = "StartServlet", urlPatterns = {"/start"})
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // 处理 GET 请求
    }
}
```

### 2. Filter

Filter 用于请求预处理和响应后处理：

```java
@WebFilter(filterName = "MyFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // 预处理
        chain.doFilter(request, response);
        // 后处理
    }
}
```

### 3. Listener

Listener 用于监听应用生命周期事件：

```java
@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 应用启动时执行
    }
}
```

## 前置条件

1. 需要 Servlet 容器（如 Tomcat 9+）
2. 需要 MySQL 数据库（用于 JDBC 示例）

### 数据库配置

```sql
CREATE DATABASE jdbctest;
```

修改 `JDBCTemplate.java` 中的数据库连接配置。

## 部署方式

### 1. 打包

```bash
mvn clean package
```

### 2. 部署到 Tomcat

将生成的 `target/java-web-no-spring-1.0-SNAPSHOT.war` 复制到 Tomcat 的 `webapps` 目录。

### 3. 访问

```
http://localhost:8080/java-web-no-spring-1.0-SNAPSHOT/start
```

## 学习要点

1. **Servlet 生命周期**：init() → service() → destroy()
2. **Filter 链**：多个 Filter 按配置顺序执行
3. **Listener 类型**：ServletContextListener、HttpSessionListener、ServletRequestListener
4. **JDBC 操作**：加载驱动 → 获取连接 → 执行 SQL → 处理结果 → 关闭资源

## 注意事项

- 生产环境应使用连接池管理数据库连接
- 密码不应硬编码在代码中
- 建议使用 try-with-resources 管理资源

