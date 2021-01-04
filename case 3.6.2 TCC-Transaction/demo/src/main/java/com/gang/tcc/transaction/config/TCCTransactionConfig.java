package com.gang.tcc.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname TCCTransactionConfig
 * @Description TODO
 * @Date 2020/10/31 13:49
 * @Created by zengzg
 */
@Configuration
public class TCCTransactionConfig {

    @Bean
    public TransactionRes(){
        return new RedisTransactionRepository();
    }
}
