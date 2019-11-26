package com.gang.study.swaggerv3.demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * @Classname JerseyConfig
 * @Description TODO
 * @Date 2019/11/24 18:59
 * @Created by zengzg
 */
@Component
@ApplicationPath("/rest/demo")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
    }

}
