package com.gang.conditional.demo.conditional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Classname DefaultConditional
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
public class DefaultConditional implements Condition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        logger.info("------> 进行 Conditional 判断 <-------");

        return false;
    }
}
