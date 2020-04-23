package com.gang.study.jersey.demo.config;

import org.apache.commons.lang3.ClassUtils;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Classname JerseyConfig
 * @Description TODO
 * @Date 2020/4/23 15:02
 * @Created by zengzg
 */
@Configuration
@Component
public class JerseyConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ResourceConfig getResourceConfig(JerseyProperties properties) {

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.setClassLoader(Thread.currentThread().getContextClassLoader());
        resourceConfig.property("jersey.config.server.resource.validation.ignoreErrors", "true");
        resourceConfig.setApplicationName(properties.getApplicationName());

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));

        Set<Class<?>> clazzs = new LinkedHashSet();

        Iterator var4 = properties.getJerseyPackage().iterator();

        while (var4.hasNext()) {
            String scanPackage = (String) var4.next();
            Iterator iterator = scanner.findCandidateComponents(scanPackage).iterator();

            while (iterator.hasNext()) {
                BeanDefinition beanDefinition = (BeanDefinition) iterator.next();

                Class<?> clazz = null;
                try {
                    clazz = ClassUtils.getClass(JerseyConfig.class.getClassLoader(),
                            beanDefinition.getBeanClassName());
                } catch (ClassNotFoundException e) {
                    logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
                }
                clazzs.add(clazz);
            }
        }

        resourceConfig.registerClasses(clazzs);
        if (properties.isMultiPartFeature()) {
            resourceConfig.register(MultiPartFeature.class);
        }

        return resourceConfig;
    }
}
