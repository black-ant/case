package com.gang.study.starter.demo.prop;

import com.gang.study.starter.demo.auto.manager.TemplateManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Classname DefaultProperties
 * @Description TODO
 * @Date 2021/10/3
 * @Created by zengzg
 */
@ConfigurationProperties(prefix = "com.gang.starter")
public class DefaultProperties {

    @Bean
    @ConditionalOnProperty(
            value = {"hzero.export.enable-async"},
            havingValue = "true"
    )
    public TemplateManager templateManager() {
        return new TemplateManager();
    }

}
