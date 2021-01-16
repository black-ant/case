package com.gang.study.multiplysource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Classname JpaConfigOne
 * @Description TODO
 * @Date 2021/1/16 15:57
 * @Created by zengzg
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.gang.study.multiplysource.jpa.repository",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanOne",
        transactionManagerRef = "platformTransactionManagerOne")
public class JpaConfigOne {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSourceOne;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanOne(EntityManagerFactoryBuilder builder){
        return builder.dataSource(dataSourceOne)
                .properties(jpaProperties.getProperties())
                .packages("com.gang.study.multiplysource.entity")
                .persistenceUnit("pu1")
                .build();

    }

    @Bean
    @Primary
    PlatformTransactionManager platformTransactionManagerOne(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBeanOne(builder);
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
