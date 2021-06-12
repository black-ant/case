package com.gang.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname ClassConfiguration
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Configuration
@ConfigurationProperties(prefix = "gang.class")
@Component
public class ClassConfiguration {

    private Class<? extends Throwable>[] ignoreExceptions;


    public Class<? extends Throwable>[] getIgnoreExceptions() {
        return ignoreExceptions;
    }

    public void setIgnoreExceptions(Class<? extends Throwable>[] ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }
}
