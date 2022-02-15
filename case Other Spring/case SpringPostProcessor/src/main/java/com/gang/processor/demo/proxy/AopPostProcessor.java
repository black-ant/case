package com.gang.processor.demo.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @Classname AopPostProcessor
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
//@Service
public class AopPostProcessor implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("------> AopPostProcessor postProcessBeforeInitialization <-------");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("------> AopPostProcessor postProcessAfterInitialization <-------");

        if (bean instanceof Sourceable) {
//            logger.info("------> AopPostProcessor build Aop <-------");
            AopProxyImpl proxy = new AopProxyImpl((Sourceable)bean);
            return proxy;
        }

        return bean;
    }
}
