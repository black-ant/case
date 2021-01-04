package com.myredis.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * @Classname RedisCondition
 * @Description TODO
 * @Date 2020/2/7 16:09
 * @Created by zengzg
 */
public class RedisCondition implements Condition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${gang.test.param0002:'false'}")
    private Boolean isok;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        logger.info("------> this is marches :{} <-------", isok);
        return isok;
    }
}
