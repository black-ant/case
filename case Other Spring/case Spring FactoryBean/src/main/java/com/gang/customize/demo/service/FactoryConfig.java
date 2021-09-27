package com.gang.customize.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

/**
 * @Classname FactoryConfig
 * @Description TODO
 * @Date 2021/9/25
 * @Created by zengzg
 */
@Configuration
public class FactoryConfig {




    @Bean
    public ScheduledExecutorFactoryBean scheduledExecutorFactoryBean() {
        ScheduledExecutorFactoryBean factoryBean = new ScheduledExecutorFactoryBean();
        factoryBean.setPoolSize(20);
        factoryBean.setThreadNamePrefix("i'm time task thread - ");
        return factoryBean;
    }
}
