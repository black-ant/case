package com.gang.processor.demo.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.PostConstruct;

/**
 * @Classname CommonService
 * @Description TODO
 * @Date 2021/9/25
 * @Created by zengzg
 */
public class CommonService implements InitializingBean, ApplicationRunner, BeanNameAware, BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String beanName;

    public void initMethod() {
        logger.info("------> this is in @Bean(initMethod = \"initMethod\")  <-------");
    }

    @PostConstruct
    public void init() {
        logger.info("------> this is @PostConstruct <-------");
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("------> this is InitializingBean  <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is ApplicationRunner :{} <-------", beanName);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("------> this is postProcessBeforeInitialization :{} <-------", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("------> this is postProcessAfterInitialization :{} <-------", beanName);
        return bean;
    }
}

