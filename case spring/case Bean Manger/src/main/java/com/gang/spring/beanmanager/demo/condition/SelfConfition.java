package com.gang.spring.beanmanager.demo.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Classname SelfConfition
 * @Description TODO
 * @Date 2020/9/29 16:10
 * @Created by zengzg
 */
public class SelfConfition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return true;
    }
}
