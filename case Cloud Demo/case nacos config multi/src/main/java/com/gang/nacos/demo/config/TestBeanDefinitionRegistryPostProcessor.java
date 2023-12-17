//package com.gang.nacos.demo.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        logger.info("测试一下 : 进入 TestBeanDefinitionRegistryPostProcessor # postProcessBeanDefinitionRegistry管理逻辑");
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        logger.info("测试一下 : 进入 TestBeanDefinitionRegistryPostProcessor # postProcessBeanFactory");
//    }
//}
//
