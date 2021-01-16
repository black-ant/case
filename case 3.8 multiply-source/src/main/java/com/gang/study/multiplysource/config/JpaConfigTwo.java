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
 * @Classname JpaConfigTwo
 * @Description TODO
 * @Date 2021/1/16 15:58
 * @Created by zengzg
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.gang.study.multiplysource.jpa.repositorytwo",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanTwo",
        transactionManagerRef = "platformTransactionManagerTwp")
public class JpaConfigTwo {

    @Autowired
    @Qualifier("secondDatasource")
    private DataSource dataSourceOne;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanTwo(EntityManagerFactoryBuilder builder){
        return builder.dataSource(dataSourceOne)
                .properties(jpaProperties.getProperties())
                .packages("com.gang.study.multiplysource.entityTwo")
                .persistenceUnit("pu1")
                .build();

    }

    @Bean
    PlatformTransactionManager platformTransactionManagerTwp(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBeanTwo(builder);
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
