package com.gang.cloud.template.demo.config;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.dubbo.remoting.TimeoutException;
import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.spring.ConfigurableCoordinatorAspect;
import org.mengyun.tcctransaction.spring.ConfigurableTransactionAspect;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @Classname SpringJdbcTransactionRepository
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
@Configuration
public class TccDefaultConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public DefaultRecoverConfig defaultRecoverConfig() {
        DefaultRecoverConfig config = new DefaultRecoverConfig();
        config.setMaxRetryCount(30);
        config.setCronExpression("0/30 * * * * ?");
        config.setRecoverDuration(60);
        config.setDelayCancelExceptions(CollectionUtil.set(false, TimeoutException.class));
        return config;
    }

    @Bean
    @Autowired
    public SpringJdbcTransactionRepository springJdbcTransactionRepository(DataSource dataSource) {
        logger.info("------> [加载 SpringJdbcTransactionRepository ] <-------");
        SpringJdbcTransactionRepository repository = new SpringJdbcTransactionRepository();
        repository.setDataSource(dataSource);
        repository.setDomain("ORDER");
        repository.setTbSuffix("_ORD");
        return repository;
    }


    @Bean(initMethod = "init")
    public SpringTransactionConfigurator getSpringTransactionConfigurator() {
        SpringTransactionConfigurator springTransactionConfigurator = new SpringTransactionConfigurator();
        return springTransactionConfigurator;
    }

    @Bean(initMethod = "init")
    public ConfigurableTransactionAspect getConfigurableTransactionAspect() {
        ConfigurableTransactionAspect springTransactionConfigurator = new ConfigurableTransactionAspect();
        springTransactionConfigurator.setTransactionConfigurator(getSpringTransactionConfigurator());
        return springTransactionConfigurator;
    }

    @Bean(initMethod = "init")
    public ConfigurableCoordinatorAspect getConfigurableCoordinatorAspect() {
        ConfigurableCoordinatorAspect configurableCoordinatorAspect = new ConfigurableCoordinatorAspect();
        configurableCoordinatorAspect.setTransactionConfigurator(getSpringTransactionConfigurator());
        return configurableCoordinatorAspect;
    }


    @Bean
    public TransactionRecovery getTransactionRecovery() {
        TransactionRecovery configurableCoordinatorAspect = new TransactionRecovery();
        configurableCoordinatorAspect.setTransactionConfigurator(getSpringTransactionConfigurator());
        return configurableCoordinatorAspect;
    }

    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    @Bean(initMethod = "init")
    public RecoverScheduledJob getRecoverScheduledJob() {
        RecoverScheduledJob schedulerFactoryBean = new RecoverScheduledJob();
        schedulerFactoryBean.setTransactionRecovery(getTransactionRecovery());
        schedulerFactoryBean.setTransactionConfigurator(getSpringTransactionConfigurator());
        schedulerFactoryBean.setScheduler(getSchedulerFactoryBean().getScheduler());
        return schedulerFactoryBean;
    }


}
