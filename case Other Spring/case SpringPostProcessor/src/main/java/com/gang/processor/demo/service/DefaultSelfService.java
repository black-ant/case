package com.gang.processor.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @Classname DefaultSelfService
 * @Description TODO
 * @Date 2021/9/25
 * @Created by zengzg
 */
@Service
public class DefaultSelfService implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("defaultSelfService")) {
            logger.info("------> this is postProcessBeforeInitialization :{} <-------", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("defaultSelfService")) {
            logger.info("------> this is postProcessAfterInitialization :{} <-------", beanName);
        }
        return bean;
    }
}
