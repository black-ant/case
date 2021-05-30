package com.gang.tcc.product.config;

import com.google.common.collect.Sets;
import com.zaxxer.hikari.HikariDataSource;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname TccConfig
 * @Description TODO
 * @Date 2021/5/28
 * @Created by zengzg
 */
@Configuration
public class TccConfig {

    @Bean
    public DefaultRecoverConfig defaultRecoverConfig() {
        DefaultRecoverConfig defaultRecoverConfig = new DefaultRecoverConfig();
        defaultRecoverConfig.setMaxRetryCount(30);   //最大重试次数
        defaultRecoverConfig.setRecoverDuration(30); //恢复持续时间
        defaultRecoverConfig.setCronExpression("0/30 * * * * ?"); //每30秒检查一次是否需要恢复(检查对应的日志表有无需要恢复的数据)//每30秒检查一次是否需要恢复
        defaultRecoverConfig.setDelayCancelExceptions(Sets.newHashSet(org.apache.dubbo.remoting.TimeoutException.class));
        return defaultRecoverConfig;
    }

    @Bean("transactionRepository")
    public SpringJdbcTransactionRepository springJdbcTransactionRepository() {
        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();

        springJdbcTransactionRepository.setDomain("CONSUMER");    //domain
        springJdbcTransactionRepository.setTbSuffix("_CONSUMER"); //配置tcc日志表名称后缀：这里为：tcc_transaction_consumer

        //tcc所需分布式事务日志数据源（也可以使用其他数据源框架）
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/tcc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=CTT");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root");
        springJdbcTransactionRepository.setDataSource(hikariDataSource);

        return springJdbcTransactionRepository;
    }

}

