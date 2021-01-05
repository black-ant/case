package com.gang.study.spring.cache.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Classname DefaultLogic
 * @Description TODO
 * @Date 2020/6/18 9:39
 * @Created by zengzg
 */
@Service
public class DefaultLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Cacheable(cacheNames = "person", key = "#key")
    public Integer get(String key) {
        Integer randomInt = new Random().nextInt(9);
        logger.info("------> this is  <-------");
        return randomInt;
    }

    @CachePut(cacheNames = "person", key = "#key")
    public Integer put(String key, Integer value) {
        return value;
    }

    @CacheEvict(cacheNames = "person", allEntries = true)
    public void deleteAll() {
        logger.info("------> 缓存全部清除 <-------");
    }

    @CacheEvict(cacheNames = "person", key = "#key")
    public void delete(String key) {
        logger.info("------> 缓存全部清除 <-------");
    }

}
