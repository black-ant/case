package com.tcc.demo.core.config;

import javax.sql.DataSource;

import org.mengyun.tcctransaction.TransactionRepository;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = "classpath:tcc-transaction.xml")
public class TccDataSourceConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TccDataSourceProperties properties;

    @Bean
    public TransactionRepository transactionRepository() {
        logger.info("------> 构建  TransactionRepository <-------");
        SpringJdbcTransactionRepository repository = new SpringJdbcTransactionRepository();
        repository.setDataSource(tccDataSource());
        return repository;
    }

    /**
     * 数据源
     *
     * @return
     */
    public DataSource tccDataSource() {
        logger.info("------> 构建  DataSource <-------");
        return DataSourceBuilder.create()
                .type(properties.getType())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

}
