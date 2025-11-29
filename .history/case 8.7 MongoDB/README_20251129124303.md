# MongoDB Demo

## 项目说明

Spring Boot MongoDB 示例，演示 NoSQL 文档数据库的使用。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring Data MongoDB
- MongoDB 6.x

## MongoDB vs 关系型数据库

| 特性 | MongoDB | MySQL |
|------|---------|-------|
| 数据模型 | 文档 (BSON) | 表 (行列) |
| Schema | 灵活 | 固定 |
| 扩展性 | 水平扩展 | 垂直扩展 |
| 事务 | 支持 | 完整支持 |
| 适用场景 | 灵活数据 | 结构化数据 |

## 项目结构

```
src/main/java/com/gang/mongodb/demo/
├── DemoApplication.java          # 启动类
├── config/
│   └── MongoDBConfig.java        # MongoDB 配置
├── controller/
│   ├── UserController.java       # 用户控制器
│   ├── AggController.java        # 聚合控制器
│   └── ChangeController.java     # Change Stream 控制器
├── entity/
│   ├── User.java                 # 用户实体
│   └── Person.java               # 人员实体
├── repository/
│   └── UserRepositoryImpl.java   # 自定义仓库实现
└── service/
    └── ChangeStreamServiceImpl.java  # Change Stream 服务
```

## 快速开始

### 安装 MongoDB

```bash
docker run -d --name mongodb -p 27017:27017 mongo:6
```

### 配置连接

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/demo
```

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

### 实体定义

```java
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private List<String> hobbies;
}
```

### CRUD 操作

```java
@Autowired
private MongoTemplate mongoTemplate;

// 插入
mongoTemplate.insert(user);

// 查询
Query query = new Query(Criteria.where("name").is("Alice"));
List<User> users = mongoTemplate.find(query, User.class);

// 更新
Update update = new Update().set("age", 30);
mongoTemplate.updateFirst(query, update, User.class);

// 删除
mongoTemplate.remove(query, User.class);
```

### 聚合查询

```java
Aggregation agg = Aggregation.newAggregation(
    Aggregation.match(Criteria.where("age").gt(18)),
    Aggregation.group("city").count().as("count"),
    Aggregation.sort(Sort.Direction.DESC, "count")
);

List<Result> results = mongoTemplate.aggregate(agg, "users", Result.class).getMappedResults();
```

## 学习资源

- [MongoDB 官方文档](https://www.mongodb.com/docs/)
- [Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)

