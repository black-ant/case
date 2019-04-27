package com.antredis.cluster.service;

import com.antredis.cluster.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/26 11:50
 * @Version 1.0
 **/
@Service
public class CacheService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisUtil redisUtil;

    public String saveCache(String type, String data) {
        logger.info("save data is :{}", data);
        redisUtil.set(type, data);
        return "";
    }

    public Object getCache(String key) {
        logger.info("get data is :{}", key);
        return redisUtil.get(key);
    }
}
