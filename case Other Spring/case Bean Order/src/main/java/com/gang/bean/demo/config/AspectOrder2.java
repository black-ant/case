package com.gang.bean.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectOrder {

    @Order(1)
    @Around("execution(* com.gang.bean.demo.service.BeanOne.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 在执行目标方法之前的操作
        Object result = joinPoint.proceed();
        // 在执行目标方法之后的操作
        return result;
    }

}
