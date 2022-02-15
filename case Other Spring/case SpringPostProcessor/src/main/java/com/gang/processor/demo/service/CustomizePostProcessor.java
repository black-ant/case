package com.gang.processor.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomizePostProcessor implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("------> CustomizePostProcessor postProcessBeforeInitialization <-------");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("------> CustomizePostProcessor postProcessAfterInitialization <-------");
        return bean;
    }
}
