package com.gang.redis.lua.demo.to;

import org.springframework.data.redis.core.script.RedisScript;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname RedisScriptContainer
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
public class RedisScriptHolder<T> {

    private volatile ConcurrentHashMap<String, RedisScript<T>> scriptContainer = new ConcurrentHashMap();


    public void addRedisScript(String flowKey, RedisScript redisScript) {
        scriptContainer.put(flowKey, redisScript);
    }
}
