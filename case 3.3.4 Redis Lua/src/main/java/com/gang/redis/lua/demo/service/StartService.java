package com.gang.redis.lua.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MoneyTransferService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("------> [测试 LUA 脚本的运行情况] <-------");
        // 测试 Redis


        // 案例一 : 执行简单脚本
        logger.info("------> 使用简单脚本 <-------");
        for (int i = 0; i < 100; i++) {
            sample000();
        }

        // 案例二 : 调用本地脚本
        for (int i = 0; i < 100; i++) {
            sample001(i);
            sample002();
        }


        // 案例三 : 调用远程脚本
//        logger.info("------> [调用远程脚本] <-------");
//        sample003();

        // 其他案例
//        booleanSimple();
    }

    public void sample000() {

        String lockKey = "Sample:" + new Random().nextInt(99);
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>("redis.call('set', KEYS[1], ARGV[1])", Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), "Success");
        System.out.println(result);
    }


    public void sample001(int num) {
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/sample001.lua")));
        // 指定返回类型
        redisScript.setResultType(Long.class);

        String lockKey = "Random:" + num;
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), "test" + new Random().nextInt(2));
        System.out.println(result);

    }

    public void sample002() {
        String lockKey = "Random:" + new Random().nextInt(99);
        String UUID = String.valueOf(new Random().nextInt(2));
//        boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, UUID, 3, TimeUnit.MINUTES);
//        if (!success) {
//            System.out.println("锁已存在");
//        }
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/sample002.lua")));
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), "test" + UUID);
        System.out.println(result);
    }

    public void sample003() {

        String scriptSha1 = "EVALSHA 64270070bfc171bced1827004576b539dd1748e7 1 username 50 3";

//        LettuceConnection lettuceConnection = (LettuceConnection) redisTemplate.getConnectionFactory().getConnection();
//        Object evalsha = lettuceConnection.evalSha(scriptSha1, 1, Arrays.asList(params));

        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                DefaultStringRedisConnection newConnection = new DefaultStringRedisConnection(connection);
                return newConnection.scriptLoad(scriptSha1);
            }
        });
    }


    public void booleanSimple() {
        logger.info("------> 执行 Lua 基本逻辑 StartService <-------");

        // initialize few accounts
        this.redisTemplate.opsForHash().put("account", "a", "100");
        this.redisTemplate.opsForHash().put("account", "b", "20");

        // transfer money with lua script
        this.service.transfer("a", "b", 20);

        // check the results
        logger.info("------> [从 Redis 中获取参数 a:{}] <-------", this.redisTemplate.opsForHash().get("account", "a"));
        logger.info("------> [从 Redis 中获取参数 b:{}] <-------", this.redisTemplate.opsForHash().get("account", "b"));
    }
}
