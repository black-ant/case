package org.mengyun.tcctransaction.sample.dubbo.config;

import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.spring.ConfigurableCoordinatorAspect;
import org.mengyun.tcctransaction.spring.ConfigurableTransactionAspect;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Classname SpringConfig
 * @Description TODO
 * @Date 2021/3/23
 * @Created by zengzg
 */
@EnableAspectJAutoProxy
@Configuration
public class SpringConfig {

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
