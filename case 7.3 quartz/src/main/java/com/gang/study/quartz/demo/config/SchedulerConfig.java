package com.gang.study.quartz.demo.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @Classname SchedulerConfig
 * @Description TODO
 * @Date 2021/2/10 14:13
 * @Created by zengzg
 */
@Configuration
public class SchedulerConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(name = "properties")
    public Properties quartzProperties() throws IOException {
        logger.info("------> doLoad Properties <-------");
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean(name = "schedulerFactory")
    public SchedulerFactory SchedulerFactory(Properties properties) throws IOException, SchedulerException {
        logger.info("------> doLoad SchedulerFactory <-------");
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
        return schedulerFactory;
    }

    /**
     * 通过SchedulerFactory获取Scheduler的实例
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(SchedulerFactory schedulerFactory) throws IOException, SchedulerException {
        logger.info("------> doLoad Scheduler <-------");
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
