package org.mengyun.tcctransaction.sample.dubbo.config;

import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Classname SpringJdbcTransactionRepository
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
@Configuration
public class TccRedConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public DefaultRecoverConfig defaultRecoverConfig() {
        DefaultRecoverConfig config = new DefaultRecoverConfig();
        config.setMaxRetryCount(30);
        config.setCronExpression("0/30 * * * * ?");
        config.setRecoverDuration(60);
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
}
