//package com.gang.nacos.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.PropertyValues;
//import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.beans.PropertyDescriptor;
//
//@Slf4j
//@Component
//public class TestInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
//
//
//    @Override
//    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessBeforeInstantiation");
//        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
//    }
//
//    @Override
//    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessAfterInstantiation");
//        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
//    }
//
//    @Override
//    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessProperties");
//        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
//    }
//
//    @Override
//    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessPropertyValues");
//        return InstantiationAwareBeanPostProcessor.super.postProcessPropertyValues(pvs, pds, bean, beanName);
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessBeforeInitialization");
//        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.info("测试一下 : 进入 TestInstantiationAwareBeanPostProcessor # postProcessAfterInitialization");
//        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//    }
//}
//
