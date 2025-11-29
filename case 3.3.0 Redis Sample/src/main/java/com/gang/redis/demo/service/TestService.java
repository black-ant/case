package com.gang.redis.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 操作测试服务
 * <p>
 * 演示 Spring Data Redis 的基本操作：
 * <ul>
 *     <li>StringRedisTemplate - 字符串专用模板</li>
 *     <li>opsForValue() - String 操作</li>
 *     <li>opsForHash() - Hash 操作</li>
 *     <li>opsForList() - List 操作</li>
 *     <li>opsForSet() - Set 操作</li>
 *     <li>opsForZSet() - ZSet 操作</li>
 * </ul>
 * </p>
 * 
 * <h3>StringRedisTemplate vs RedisTemplate：</h3>
 * <ul>
 *     <li>StringRedisTemplate - key 和 value 都是 String</li>
 *     <li>RedisTemplate - 支持任意对象序列化</li>
 * </ul>
 *
 * @author zengzg
 * @since 2021/8/13
 */
@Component
public class TestService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 应用启动后执行 Redis 操作演示
     *
     * @param args 启动参数
     */
    @Override
    public void run(ApplicationArguments args) {
        logger.info("=== Starting Redis Demo ===");
        
        try {
            // 演示 String 操作
            stringOperations();
            
            logger.info("=== Redis Demo Completed ===");
        } catch (Exception e) {
            logger.error("Redis operation failed. Make sure Redis is running!", e);
        }
    }

    /**
     * String 类型操作演示
     */
    private void stringOperations() {
        logger.info("--- String Operations ---");
        
        // 设置值
        redisTemplate.opsForValue().set("demo:name", "Spring Redis Demo");
        logger.info("Set demo:name = Spring Redis Demo");
        
        // 设置值并指定过期时间
        redisTemplate.opsForValue().set("demo:temp", "temporary", 60, TimeUnit.SECONDS);
        logger.info("Set demo:temp with 60s TTL");
        
        // 获取值
        String value = redisTemplate.opsForValue().get("demo:name");
        logger.info("Get demo:name = {}", value);
        
        // 计数器
        redisTemplate.opsForValue().set("demo:counter", "0");
        Long counter = redisTemplate.opsForValue().increment("demo:counter");
        logger.info("Increment demo:counter = {}", counter);
        
        // 如果不存在则设置（分布式锁常用）
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent("demo:lock", "locked", 30, TimeUnit.SECONDS);
        logger.info("SetIfAbsent demo:lock = {}", success);
    }

    /**
     * Hash 类型操作演示
     */
    public void hashOperations() {
        logger.info("--- Hash Operations ---");
        
        String key = "demo:user:1";
        
        // 设置单个字段
        redisTemplate.opsForHash().put(key, "name", "Alice");
        redisTemplate.opsForHash().put(key, "age", "25");
        
        // 获取单个字段
        Object name = redisTemplate.opsForHash().get(key, "name");
        logger.info("User name: {}", name);
        
        // 获取所有字段
        logger.info("User: {}", redisTemplate.opsForHash().entries(key));
    }

    /**
     * List 类型操作演示
     */
    public void listOperations() {
        logger.info("--- List Operations ---");
        
        String key = "demo:queue";
        
        // 左侧入队
        redisTemplate.opsForList().leftPush(key, "message1");
        redisTemplate.opsForList().leftPush(key, "message2");
        
        // 右侧出队
        String msg = redisTemplate.opsForList().rightPop(key);
        logger.info("Popped message: {}", msg);
    }

    /**
     * Set 类型操作演示
     */
    public void setOperations() {
        logger.info("--- Set Operations ---");
        
        String key = "demo:tags";
        
        // 添加元素
        redisTemplate.opsForSet().add(key, "java", "spring", "redis");
        
        // 获取所有元素
        logger.info("Tags: {}", redisTemplate.opsForSet().members(key));
        
        // 判断是否存在
        Boolean exists = redisTemplate.opsForSet().isMember(key, "java");
        logger.info("Contains java: {}", exists);
    }
}
