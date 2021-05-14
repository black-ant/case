package com.gang.study.sharding.boot.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Classname TransactionConfig
 * @Description TODO
 * @Date 2021/5/12
 * @Created by zengzg
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager(final DataSource dataSource) {
        logger.info("------> build  PlatformTransactionManager success<-------");
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        logger.info("------> build  JdbcTemplate success<-------");
        return new JdbcTemplate(dataSource);
    }
}
