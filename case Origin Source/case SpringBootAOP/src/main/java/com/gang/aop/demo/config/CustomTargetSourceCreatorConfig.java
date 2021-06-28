package com.gang.aop.demo.config;

import com.gang.aop.demo.service.DefaultCustomerTargetSourceCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * @Classname CustomTargetSourceCreatorConfig
 * @Description TODO
 * @Date 2021/6/28
 * @Created by zengzg
 */
@Component
public class CustomTargetSourceCreatorConfig implements BeanPostProcessor, PriorityOrdered, BeanFactoryAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanFactory beanFactory;

    @Override
    public int getOrder() {
        return 45;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("------> 进入 CustomTargetSourceCreatorConfig 初始化加载逻辑 , 返回默认创建者  <-------");
        if (bean instanceof AnnotationAwareAspectJAutoProxyCreator) {
            AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator = (AnnotationAwareAspectJAutoProxyCreator) bean;
            DefaultCustomerTargetSourceCreator customTargetSourceCreator = new DefaultCustomerTargetSourceCreator();
            customTargetSourceCreator.setBeanFactory(beanFactory);
            annotationAwareAspectJAutoProxyCreator.setCustomTargetSourceCreators(customTargetSourceCreator);
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

