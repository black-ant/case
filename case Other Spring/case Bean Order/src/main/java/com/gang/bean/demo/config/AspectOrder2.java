package com.gang.bean.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class AspectOrder2 {


//    @Around("execution(* com.gang.bean.demo.service.BeanOne.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("AspectOrder2 执行目标方法之前的操作");
        // 在执行目标方法之前的操作
        Object result = joinPoint.proceed();
        // 在执行目标方法之后的操作
        return result;
    }

}
