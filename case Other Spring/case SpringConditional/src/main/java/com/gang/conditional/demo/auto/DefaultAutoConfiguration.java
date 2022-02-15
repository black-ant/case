package com.gang.conditional.demo.auto;

import com.gang.conditional.demo.conditional.DefaultConditional;
import com.gang.conditional.demo.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DefaultConfiguration
 * @Description TODO
 * @Date 2021/10/3
 * @Created by zengzg
 */
@Configuration
public class DefaultAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @Conditional(DefaultConditional.class)
    public ConfigBean getConfigBean() {
        logger.info("------> this is in DefaultConfig <-------");
        return new ConfigBean();
    }

}
