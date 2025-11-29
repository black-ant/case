# Java Application - 纯 Java 应用示例

## 项目说明

这是一个不依赖任何框架的纯 Java 应用示例，演示原生 JDBC 数据库操作。

## 技术栈

- Java 11
- JDBC
- MySQL 8.0

## 项目结构

```
src/
├── Main.java       # 应用入口
└── DBHelper.java   # 数据库帮助类
```

## 前置条件

### 1. 数据库准备

```sql
-- 创建数据库
CREATE DATABASE gang CHARACTER SET utf8mb4;

-- 使用数据库
USE gang;

-- 创建用户表
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    user_org VARCHAR(100)
);

-- 插入测试数据
INSERT INTO user (username, user_org) VALUES ('gang', 'Tech Department');
```

### 2. 修改数据库配置

编辑 `DBHelper.java` 中的数据库连接配置：

```java
private static final String URL = "jdbc:mysql://127.0.0.1:3306/gang?...";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

## 运行方式

### 方式一：使用 Maven

```bash
# 编译
mvn compile

# 运行
mvn exec:java -Dexec.mainClass="Main"

# 或者打包后运行
mvn package
java -jar target/java-application-1.0-SNAPSHOT.jar
```

### 方式二：直接编译运行

```bash
# 下载 MySQL 驱动到当前目录
# 编译
javac -cp mysql-connector-java-8.0.33.jar src/*.java -d out

# 运行
java -cp out:mysql-connector-java-8.0.33.jar Main
```

## JDBC 操作流程

```
1. 加载驱动 ──> 2. 获取连接 ──> 3. 创建 Statement
       │                              │
       │                              ▼
       │                    4. 执行 SQL
       │                              │
       │                              ▼
       │                    5. 处理 ResultSet
       │                              │
       ▼                              ▼
                          6. 关闭资源 (逆序)
```

## 学习要点

### 1. Statement vs PreparedStatement

| 特性 | Statement | PreparedStatement |
|------|-----------|-------------------|
| 预编译 | 否 | 是 |
| SQL 注入防护 | 无 | 有 |
| 效率 | 低 | 高 |
| 参数绑定 | 字符串拼接 | 占位符 ? |

### 2. 资源关闭顺序

```
关闭顺序：ResultSet → Statement → Connection
（与创建顺序相反）
```

### 3. 使用 try-with-resources (Java 7+)

```java
try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) {
    // 使用资源
} // 自动关闭资源
```

## 注意事项

- 生产环境应使用连接池（HikariCP、Druid 等）
- 密码不应硬编码，应从配置文件或环境变量读取
- 建议使用 try-with-resources 自动管理资源
- MySQL 8.0 驱动类名改为 `com.mysql.cj.jdbc.Driver`

