# HikariCP Demo

## 项目说明

Spring Boot HikariCP 连接池示例，演示数据库连接池的配置和使用。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- HikariCP (Spring Boot 默认)
- Spring Data JPA
- MySQL

## 什么是 HikariCP？

HikariCP 是一个高性能的 JDBC 连接池，名称来源于日语「光」(Hikari)。

### 特点

| 特性 | 说明 |
|------|------|
| 高性能 | 目前最快的连接池 |
| 轻量级 | 约 130KB |
| 可靠 | 连接健康检查 |
| 默认 | Spring Boot 2.x 默认连接池 |

### 连接池对比

| 连接池 | 性能 | 功能 | 推荐场景 |
|--------|------|------|----------|
| HikariCP | ⭐⭐⭐⭐⭐ | 基础 | 追求性能 |
| Druid | ⭐⭐⭐⭐ | 监控丰富 | 需要 SQL 监控 |
| C3P0 | ⭐⭐⭐ | 成熟 | 老项目兼容 |
| DBCP2 | ⭐⭐⭐ | 稳定 | Apache 项目 |

## 项目结构

```
src/main/java/com/gang/study/hikari/demo/
├── DemoApplication.java          # 启动类
├── entity/
│   └── UserEntity.java           # 用户实体
├── repository/
│   └── UserRepository.java       # 用户仓库
└── service/
    ├── UserController.java       # 用户控制器
    └── UserService.java          # 用户服务
```

## 快速开始

### 配置数据库

编辑 `application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hikari_demo?useSSL=false&serverTimezone=UTC
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
    
    # HikariCP 配置
    hikari:
      # 连接池名称
      pool-name: HikariPool
      # 最小空闲连接数
      minimum-idle: 5
      # 最大连接数
      maximum-pool-size: 10
      # 空闲连接超时时间（毫秒）
      idle-timeout: 600000
      # 连接最大生命周期（毫秒）
      max-lifetime: 1800000
      # 连接超时时间（毫秒）
      connection-timeout: 30000
      # 连接测试查询
      connection-test-query: SELECT 1
```

### 启动应用

```bash
mvn spring-boot:run
```

## HikariCP 配置详解

### 必要配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| maximum-pool-size | 最大连接数 | 10 |
| minimum-idle | 最小空闲连接 | 与 max 相同 |
| connection-timeout | 获取连接超时 | 30s |

### 性能优化配置

| 配置项 | 说明 | 建议值 |
|--------|------|--------|
| idle-timeout | 空闲连接超时 | 600000 (10分钟) |
| max-lifetime | 连接最大生命周期 | 1800000 (30分钟) |
| leak-detection-threshold | 连接泄漏检测阈值 | 0 (禁用) |

### 监控配置

```yaml
spring:
  datasource:
    hikari:
      register-mbeans: true  # 注册 JMX MBeans
```

## 连接池大小计算

推荐公式：
```
connections = ((core_count * 2) + effective_spindle_count)
```

- core_count: CPU 核心数
- effective_spindle_count: 磁盘主轴数（SSD 可以认为是 1）

## 代码示例

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // 自动从连接池获取连接
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    // 事务操作，连接在事务结束后归还
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
```

## 常见问题

### 1. 连接超时

增加 connection-timeout 或检查数据库服务。

### 2. 连接耗尽

增加 maximum-pool-size 或优化慢查询。

### 3. 连接泄漏

启用 leak-detection-threshold 检测泄漏。

## 学习资源

- [HikariCP GitHub](https://github.com/brettwooldridge/HikariCP)
- [HikariCP 配置说明](https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby)

