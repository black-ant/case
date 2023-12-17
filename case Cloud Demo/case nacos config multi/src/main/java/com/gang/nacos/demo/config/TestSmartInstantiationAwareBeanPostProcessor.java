//package com.gang.nacos.demo.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Constructor;
//
//@Component
//public class TestSmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
//
//    @Override
//    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("[TestSmartInstantiationAwareBeanPostProcessor] predictBeanType " + beanName);
//        return beanClass;
//    }
//
//    @Override
//    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("[TestSmartInstantiationAwareBeanPostProcessor] determineCandidateConstructors " + beanName);
//        return null;
//    }
//
//    @Override
//    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
//        System.out.println("[TestSmartInstantiationAwareBeanPostProcessor] getEarlyBeanReference " + beanName);
//        return bean;
//    }
//}
//
//
