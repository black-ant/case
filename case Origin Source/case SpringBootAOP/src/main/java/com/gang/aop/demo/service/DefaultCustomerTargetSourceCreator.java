package com.gang.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.target.AbstractBeanFactoryBasedTargetSourceCreator;
import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @Classname CustomerTargetSource
 * @Description TODO
 * @Date 2021/6/28
 * @Created by zengzg
 */
public class DefaultCustomerTargetSourceCreator extends AbstractBeanFactoryBasedTargetSourceCreator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class<?> beanClass, String beanName) {
        logger.info("------> CustomerTargetSourceCreator build : [进入流程创建过程 , 返回默认资源类 DefaultCustomTargetSource] <-------");
        if (getBeanFactory() instanceof ConfigurableListableBeanFactory) {
            if (beanClass.isAssignableFrom(OtherService.class)) {
                return new DefaultCustomTargetSource();
            }
        }
        return null;
    }
}

