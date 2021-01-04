package com.myredis.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname ScanController
 * @Description TODO
 * @Date 2020/12/17 17:51
 * @Created by zengzg
 */
@RestController
@RequestMapping("scan")
public class ScanController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @return
     */
    @GetMapping("test")
    public String test() {

        logger.info("------> test <-------");
        redisTemplate.execute(new RedisCallback<Set<Object>>() {
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<Object> binaryKeys = new HashSet<>();

                ScanOptions options = new ScanOptions.ScanOptionsBuilder().match("test*").count(2).build();
                Cursor<byte[]> cursor = connection.scan(options);
                while (cursor.hasNext()) {
                    String key = new String(cursor.next());
                    logger.info("------> this is key :{}<-------", key);
                }
                return binaryKeys;
            }
        });
        return "success";
    }
}
