package com.gang.processor.demo.manager;

import com.gang.processor.demo.proxy.AopProxyImpl;
import com.gang.processor.demo.proxy.Sourceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @Classname AopPostProcessor
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
@Service
public class ManagerPostProcessor implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanManager beanManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("------> ManagerPostProcessor postProcessBeforeInitialization <-------");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("------> ManagerPostProcessor postProcessAfterInitialization <-------");

        if (bean instanceof TypeBean) {
            logger.info("------> AopPostProcessor build ManagerPostProcessor <-------");
            beanManager.add(beanName, (TypeBean) bean);
        }

        return bean;
    }
}
