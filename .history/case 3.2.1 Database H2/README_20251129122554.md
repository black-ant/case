# H2 Database Demo

## 项目说明

H2 内存数据库示例，演示嵌入式数据库的多种使用方式。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- H2 Database 2.2.x

## 什么是 H2 数据库？

H2 是一个纯 Java 编写的轻量级关系型数据库：

| 特性 | 说明 |
|------|------|
| 纯 Java | 跨平台，无需安装 |
| 多模式 | 内存、嵌入式、服务器 |
| 小巧 | JAR 文件约 2MB |
| 快速 | 性能优秀 |
| 兼容 | 支持标准 SQL |

## 运行模式

### 1. 内存模式 (Memory)

```
jdbc:h2:mem:testdb
```

- 数据存储在内存中
- 应用关闭后数据丢失
- 适合单元测试

### 2. 嵌入式模式 (Embedded)

```
jdbc:h2:~/testdb
jdbc:h2:./data/testdb
```

- 数据持久化到文件
- 单进程访问
- 适合小型应用

### 3. 服务器模式 (Server)

```
jdbc:h2:tcp://localhost:9092/~/testdb
```

- 支持多客户端连接
- 需要启动 H2 服务器

## 项目结构

```
src/main/java/com/gang/study/h2base/demo/
├── DemoApplication.java              # 启动类
└── logic/
    ├── H2BaseMemeryLogic.java        # 内存模式示例
    └── H2BaseClientLogic.java        # 客户端模式示例
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 运行测试

```bash
mvn test
```

## 代码示例

### 内存模式

```java
// 连接 URL（DB_CLOSE_DELAY=-1 保持数据库不关闭）
String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

try (Connection conn = DriverManager.getConnection(url, "sa", "");
     Statement stmt = conn.createStatement()) {
    
    // 建表
    stmt.execute("CREATE TABLE users (id INT, name VARCHAR(50))");
    
    // 插入
    stmt.execute("INSERT INTO users VALUES(1, 'Alice')");
    
    // 查询
    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    while (rs.next()) {
        System.out.println(rs.getString("name"));
    }
}
```

### H2 控制台

Spring Boot 集成 H2 控制台：

```properties
# application.properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:testdb
```

访问：http://localhost:8080/h2-console

## 配置选项

| 参数 | 说明 |
|------|------|
| DB_CLOSE_DELAY=-1 | 保持数据库不关闭 |
| MODE=MySQL | 兼容 MySQL 语法 |
| TRACE_LEVEL_FILE=3 | 日志级别 |
| INIT=RUNSCRIPT FROM 'init.sql' | 初始化脚本 |

## 使用场景

1. **单元测试** - 无需外部数据库依赖
2. **开发环境** - 快速启动，无需配置
3. **原型开发** - 快速验证想法
4. **小型应用** - 嵌入式部署

## 学习资源

- [H2 官方文档](https://h2database.com/html/main.html)
- [Spring Boot H2 指南](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.h2-web-console)

