package com.gang.flowable.demo.config;


import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(name = "processEngineConfiguration")
    public StandaloneProcessEngineConfiguration getProcessEngineConfiguration() {
        return new StandaloneProcessEngineConfiguration();
    }

    @Bean
    public ProcessEngineFactoryBean getProcessEngineFactoryBean(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        return new ProcessEngineFactoryBean();
    }

    @Bean
    public SpringProcessEngineConfiguration getSpringProcessEngineConfiguration() {
        return new SpringProcessEngineConfiguration();
    }
}
