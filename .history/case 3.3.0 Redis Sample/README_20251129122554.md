# Redis Sample

## 项目说明

Spring Boot Redis 示例，演示 Spring Data Redis 的基本用法。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring Data Redis
- Lettuce (默认客户端)

## 前置条件

### 安装 Redis

**Windows (使用 Docker)**：
```bash
docker run -d --name redis -p 6379:6379 redis:7
```

**Linux/Mac**：
```bash
# Ubuntu
sudo apt install redis-server

# Mac
brew install redis
```

### 启动 Redis

```bash
redis-server
```

## 项目结构

```
src/main/java/com/gang/redis/demo/
├── DemoApplication.java          # 启动类
└── service/
    └── TestService.java          # Redis 操作服务
```

## Redis 数据类型

| 类型 | 说明 | 操作类 | 使用场景 |
|------|------|--------|----------|
| String | 字符串 | opsForValue() | 缓存、计数器、分布式锁 |
| Hash | 哈希表 | opsForHash() | 对象存储 |
| List | 列表 | opsForList() | 消息队列、最新列表 |
| Set | 集合 | opsForSet() | 去重、标签、共同好友 |
| ZSet | 有序集合 | opsForZSet() | 排行榜、延迟队列 |

## 快速开始

### 配置连接

编辑 `application.properties`：

```properties
# Redis 连接配置
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
spring.redis.database=0

# 连接池配置
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
```

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

### String 操作

```java
@Autowired
private StringRedisTemplate redisTemplate;

// 设置值
redisTemplate.opsForValue().set("key", "value");

// 设置值并指定过期时间
redisTemplate.opsForValue().set("key", "value", 60, TimeUnit.SECONDS);

// 获取值
String value = redisTemplate.opsForValue().get("key");

// 计数器
Long count = redisTemplate.opsForValue().increment("counter");

// 分布式锁
Boolean locked = redisTemplate.opsForValue()
    .setIfAbsent("lock:key", "locked", 30, TimeUnit.SECONDS);
```

### Hash 操作

```java
// 设置字段
redisTemplate.opsForHash().put("user:1", "name", "Alice");

// 获取字段
Object name = redisTemplate.opsForHash().get("user:1", "name");

// 获取所有
Map<Object, Object> user = redisTemplate.opsForHash().entries("user:1");
```

### List 操作

```java
// 入队
redisTemplate.opsForList().leftPush("queue", "message");

// 出队
String msg = redisTemplate.opsForList().rightPop("queue");

// 获取范围
List<String> list = redisTemplate.opsForList().range("queue", 0, -1);
```

### 自定义 RedisTemplate

```java
@Bean
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    
    // JSON 序列化
    Jackson2JsonRedisSerializer<Object> serializer = 
        new Jackson2JsonRedisSerializer<>(Object.class);
    
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(serializer);
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(serializer);
    
    return template;
}
```

## 常用命令

```bash
# 连接 Redis CLI
redis-cli

# 查看所有 key
keys *

# 查看 key 类型
type demo:name

# 查看 TTL
ttl demo:temp

# 删除 key
del demo:name
```

## 学习资源

- [Spring Data Redis 文档](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)
- [Redis 官方文档](https://redis.io/docs/)

