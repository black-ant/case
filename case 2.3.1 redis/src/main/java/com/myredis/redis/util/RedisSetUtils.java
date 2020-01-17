package com.myredis.redis.util;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname RedisSetUtils
 * @Description TODO
 * @Date 2020/1/8 11:30
 * @Created by zengzg
 */
@Component
public class RedisSetUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    public String hget(String key, String field) {
        Object val = redisTemplate.opsForHash().get(key, field);
        return val == null ? null : val.toString();
    }

    public <K, V> Map<K, V> hgetMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public <V> void hsetMap(String key, Map<String, V> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }


    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    //    public Map<String, String> hgetall(String key) {
    //        return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
    //            Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
    //            if (CollectionUtils.isEmpty(result)) {
    //                return new HashMap<>(0);
    //            }
    //
    //            Map<String, String> ans = new HashMap<>(result.size());
    //            for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
    //                ans.put(new String(entry.getKey()), new String(entry.getValue()));
    //            }
    //            return ans;
    //        });
    //    }

    public Map<String, String> hmget(String key, List<String> fields) {
        List<String> result = redisTemplate.<String, String>opsForHash().multiGet(key, fields);
        Map<String, String> ans = new HashMap<>(fields.size());
        int index = 0;
        for (String field : fields) {
            if (result.get(index) == null) {
                continue;
            }
            ans.put(field, result.get(index));
        }
        return ans;
    }

    /**
     * 自增
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hincr(String key, String field, long value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }


    public String listGet(String key, Long index) {
        Object val = redisTemplate.opsForList().index(key, index);
        return val.toString();
    }

    /**
     * 替换指定位置的值
     *
     * @param key
     * @param field
     * @param value
     */
    public void listSet(String key, Long field, String value) {
        redisTemplate.opsForList().set(key, field, value);
    }

    /**
     * 添加值
     *
     * @param key
     * @param value
     */
    public void listPush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public String setGet(String key) {
        Object val = redisTemplate.opsForSet().members(key);
        return val.toString();
    }

    public void setSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    //    public String sortSetGet(String key, String value, Double double) {
    //        Object val = redisTemplate.opsForZSet().add(key, value)
    //        return val.toString();
    //    }
    //
    //    public void sortSetSet(String key, String value) {
    //        redisTemplate.opsForSet().add(key, value);
    //    }


}
