package com.gang.study.feign.config.demo.config;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname ParaFeignClientsRegistryBeans
 * @Description TODO
 * @Date 2020/4/22 23:10
 * @Created by zengzg
 */
@Configuration
public class FeignClientConfig implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware,
        BeanPostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(FeignClientConfig.class);
    private FeignProperties properties;
    private BeanDefinitionRegistry registry;
    private ApplicationContext applicationContext;
    private boolean init = false;
    private static Class<?> FEIGNFACTORYBEANCLASS;

    public FeignClientConfig() {
        try {
            FEIGNFACTORYBEANCLASS = ClassUtils.getClass("org.springframework.cloud.openfeign.FeignClientFactoryBean");
        } catch (ClassNotFoundException var2) {
            throw new RuntimeException(var2);
        }
    }

    private void registerFeignClient(BeanDefinitionRegistry registry, Class<?> clazz,
                                     FeignProperties.FeignClientBean feignClientBean) {
        String className = clazz.getName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FEIGNFACTORYBEANCLASS);
        definition.addPropertyValue("url", feignClientBean.getUrl());
        definition.addPropertyValue("contextId", feignClientBean.getServerId());
        definition.addPropertyValue("path", feignClientBean.getContextPath());
        String name = feignClientBean.getServerId() + clazz.getSimpleName();
        definition.addPropertyValue("name", feignClientBean.getServerId());
        definition.addPropertyValue("type", clazz);
        definition.addPropertyValue("decode404", feignClientBean.isDecode404());
        definition.addPropertyValue("fallback", feignClientBean.getFallback());
        definition.addPropertyValue("fallbackFactory", feignClientBean.getFallbackFactory());
        definition.setAutowireMode(2);
        String alias = name + "FeignClient";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        boolean primary = feignClientBean.isPrimary();
        beanDefinition.setPrimary(primary);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, new String[]{alias});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOG.trace(beanName);
        if (!this.init) {
            this.init = true;
            this.properties =
                    (FeignProperties) this.applicationContext.getBean(FeignProperties.class);
            Map<String, FeignProperties.FeignClientBean> autoConfig = this.properties.getConfig();
            autoConfig.forEach((key, feignClientBean) -> {
                LOG.debug("start initialize microservice {} ", key);
                List<Class<?>> beanInjections = feignClientBean.getBeanInjections();
                Iterator var4 = beanInjections.iterator();

                while (var4.hasNext()) {
                    Class beanInjection = (Class) var4.next();

                    try {
                        LOG.debug("register microservice bean {} ", beanInjection);
                        this.registerFeignClient(this.registry, beanInjection, feignClientBean);
                    } catch (Exception var7) {
                        LOG.error("failure to register microservice bean ", var7);
                    }
                }

            });
        }

        return bean;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

