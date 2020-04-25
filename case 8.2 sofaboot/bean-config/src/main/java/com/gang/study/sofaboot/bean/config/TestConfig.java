package com.gang.study.sofaboot.bean.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname TestConfig
 * @Description TODO
 * @Date 2020/4/25 23:04
 * @Created by zengzg
 */
@Configuration
public class TestConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public TestService getTestService() {
        logger.info("------> get Test Service <-------");
        return new TestServiceImpl();
    }
}
