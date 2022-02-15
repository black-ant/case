package com.gang.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname LineConfiguration
 * @Description TODO
 * @Date 2021/9/28
 * @Created by zengzg
 */
@Configuration
@ConfigurationProperties(prefix = "gang.line")
@Component
public class LineConfiguration {

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
