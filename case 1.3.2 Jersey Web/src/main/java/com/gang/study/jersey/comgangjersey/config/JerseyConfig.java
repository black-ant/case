package com.gang.study.jersey.comgangjersey.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * @Classname JerseyConfig
 * @Description TODO
 * @Date 2020/1/17 14:33
 * @Created by zengzg
 */

@Component
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        //注册包的方式
        packages("com.gang");
    }
}
