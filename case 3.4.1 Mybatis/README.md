# MyBatis Demo

## 项目说明

Spring Boot MyBatis 示例，演示 MyBatis ORM 框架的完整用法。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- MyBatis 2.3.x
- MySQL 8.0
- MyBatis Generator

## 前置条件

### 数据库初始化

1. 创建数据库
2. 执行 `src/mybatistest.sql` 初始化脚本
3. 修改 `application.yml` 配置数据库连接

## 项目结构

```
src/main/java/com/mybatistest/demo/
├── DemoApplication.java          # 启动类（@MapperScan）
├── entity/
│   ├── User.java                 # 用户实体
│   └── MyOrder.java              # 订单实体
├── mapper/
│   ├── UserMapper.java           # 用户 Mapper
│   └── MyOrderMapper.java        # 订单 Mapper
├── service/
│   ├── StartService.java         # 启动服务
│   └── OrderService.java         # 订单服务
├── controller/
│   ├── UserController.java       # 用户控制器
│   └── OrderController.java      # 订单控制器
├── plugins/
│   ├── DefaultInterceptor.java   # 自定义拦截器
│   └── PluginsConfig.java        # 插件配置
└── util/
    └── UUIdGenId.java            # UUID 生成器

src/main/resources/
├── mapper/
│   ├── UserMapper.xml            # 用户 SQL 映射
│   └── MyOrderMapper.xml         # 订单 SQL 映射
├── config/
│   ├── generatorConfig.xml       # MBG 配置
│   └── mybatis-config.xml        # MyBatis 配置
└── application.yml               # 应用配置
```

## MyBatis 映射方式

### 1. XML 映射（推荐复杂 SQL）

```xml
<!-- UserMapper.xml -->
<select id="selectByPrimaryKey" resultType="User">
    SELECT * FROM user WHERE sn = #{sn}
</select>

<insert id="insert">
    INSERT INTO user (sn, username, password)
    VALUES (#{sn}, #{username}, #{password})
</insert>
```

### 2. 注解映射（适合简单 SQL）

```java
@Select("SELECT * FROM user WHERE username = #{username}")
User findByUsername(String username);

@Insert("INSERT INTO user(sn, username) VALUES(#{sn}, #{username})")
int insert(User user);

@Update("UPDATE user SET username = #{username} WHERE sn = #{sn}")
int update(User user);

@Delete("DELETE FROM user WHERE sn = #{sn}")
int delete(String sn);
```

## 动态 SQL

```xml
<select id="findByCondition" resultType="User">
    SELECT * FROM user
    <where>
        <if test="username != null">
            AND username = #{username}
        </if>
        <if test="status != null">
            AND status = #{status}
        </if>
    </where>
</select>
```

## MyBatis Generator

生成 Mapper 和实体类：

```bash
mvn mybatis-generator:generate
```

配置文件：`src/main/resources/config/generatorConfig.xml`

## 快速开始

### 配置数据库

编辑 `application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mybatis_demo?useSSL=false&serverTimezone=UTC
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.mybatistest.demo.entity
  configuration:
    map-underscore-to-camel-case: true
```

### 启动应用

```bash
mvn spring-boot:run
```

## 学习资源

- [MyBatis 官方文档](https://mybatis.org/mybatis-3/zh/)
- [MyBatis-Spring-Boot](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

