package com.gang.conditional.demo.auto;

import com.gang.conditional.demo.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DefaultConfiguration
 * @Description 某个class位于类路径上，才会实例化一个Bean
 * @Date 2021/10/3
 * @Created by zengzg
 */
@Configuration
public class OnClassConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @ConditionalOnClass(ConfigBean.class)
//    @ConditionalOnClass(RedisTemplate.class)
    public ConfigBean getConfigBean() {
        logger.info("------> 开始加载 <-------");
        return new ConfigBean();
    }

}
