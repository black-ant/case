package com.gang.conditional.demo.auto;

import com.gang.conditional.demo.config.ConfigBean;
import com.gang.conditional.demo.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DefaultConfiguration
 * @Description 仅仅在当前上下文中存在某个对象时，才会实例化一个Bean
 * @Date 2021/10/3
 * @Created by zengzg
 */
@Configuration
public class OnBeanConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public TestService getTestService() {
        return new TestService();
    }

    @Bean
    @ConditionalOnBean(TestService.class)
    public ConfigBean getConfigBean() {
        logger.info("------> 开始加载 ConditionalOnBean <-------");
        return new ConfigBean();
    }

}
