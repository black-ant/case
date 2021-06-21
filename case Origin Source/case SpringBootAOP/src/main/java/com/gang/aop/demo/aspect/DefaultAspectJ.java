package com.gang.aop.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname DefaultAspectJ
 * @Description TODO
 * @Date 2021/6/21
 * @Created by zengzg
 */
@Aspect
@Component
public class DefaultAspectJ {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* com.gang.aop.demo.service.StartService.get(..))")
    public void before() {
        logger.info("------> [进入 before 逻辑 ] <-------");
    }

    @After("execution(* com.gang.aop.demo.service.StartService.get(..))")
    public void after() {
        logger.info("------> [进入 After 逻辑 ] <-------");
    }

    @AfterReturning("execution(* com.gang.aop.demo.service.StartService.get(..))")
    public void afterReturning() {
        logger.info("------> [进入 AfterReturning 逻辑 ] <-------");
    }

    @Around("execution(* com.gang.aop.demo.service.StartService.get(..))")
    public void around(ProceedingJoinPoint pj) {
        try {
            logger.info("------> [进入 around 逻辑 - before ] <-------");
            pj.proceed();
            logger.info("------> [进入 around 逻辑 - after ] <-------");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
