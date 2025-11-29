# JPA Example

## 项目说明

Spring Data JPA 示例项目，演示 JPA 的基本用法和高级特性。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring Data JPA
- MySQL 8.0
- Hutool
- Lombok

## 前置条件

### 数据库配置

1. 安装 MySQL 数据库
2. 创建数据库和表：

```sql
CREATE DATABASE jpa_demo;
USE jpa_demo;

CREATE TABLE user_1 (
    userid INT PRIMARY KEY,
    username VARCHAR(50),
    usertype VARCHAR(20),
    isactive TINYINT DEFAULT 1,
    userlink VARCHAR(200),
    remark VARCHAR(500),
    orgid VARCHAR(50)
);

CREATE TABLE org (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(500)
);
```

3. 修改 `application.properties` 配置数据库连接

## 项目结构

```
src/main/java/com/myjpa/demo/
├── DemoApplication.java          # 启动类
├── entity/
│   ├── UserEntity.java           # 用户实体
│   ├── OrgEntity.java            # 组织实体
│   └── api/
│       └── IOrgEntity.java       # 实体接口
├── repository/
│   ├── UserRepository.java       # 用户仓库
│   └── OrgRepository.java        # 组织仓库
├── service/
│   ├── UserService.java          # 用户服务
│   ├── OrgService.java           # 组织服务
│   ├── SaveService.java          # 保存服务
│   └── TransactionService.java   # 事务服务
└── controller/
    ├── UserController.java       # 用户控制器
    ├── OrgController.java        # 组织控制器
    └── ...
```

## JPA 核心注解

| 注解 | 说明 |
|------|------|
| @Entity | 标记实体类 |
| @Table | 指定表名 |
| @Id | 主键字段 |
| @GeneratedValue | 主键生成策略 |
| @Column | 列映射配置 |
| @OneToMany | 一对多关系 |
| @ManyToOne | 多对一关系 |
| @JoinColumn | 关联列 |

## 查询方式

### 1. 方法名派生查询

```java
List<User> findByUsername(String username);
List<User> findByUsernameAndUsertype(String username, String type);
```

### 2. JPQL 查询

```java
@Query("SELECT u FROM UserEntity u WHERE u.username = :name")
List<User> findByName(@Param("name") String name);
```

### 3. 原生 SQL

```java
@Query(value = "SELECT * FROM user_1 WHERE username = ?1", nativeQuery = true)
List<User> findByNameNative(String name);
```

## 快速开始

### 配置数据库

编辑 `application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jpa_demo?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 启动应用

```bash
mvn spring-boot:run
```

### 测试接口

```bash
# 获取所有用户
curl http://localhost:8080/user/list

# 根据用户名查询
curl http://localhost:8080/user/getByName?username=admin
```

## 事务管理

```java
@Service
public class UserService {
    
    @Transactional
    public void updateUser(Integer id, String type) {
        userRepository.updateUser(type, id);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<User> users) {
        // 批量操作
    }
}
```

## 学习资源

- [Spring Data JPA 官方文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JPA 规范](https://jakarta.ee/specifications/persistence/)

