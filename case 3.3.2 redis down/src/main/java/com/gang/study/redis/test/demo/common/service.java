package com.gang.study.redis.test.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Classname service
 * @Description TODO
 * @Date 2019/12/18 16:53
 * @Created by zengzg
 */
@Service
public class service implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            template.hasKey("1");
            template.opsForValue().set("1", "00000");
            template.opsForValue().get("1");
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }

    }
}
