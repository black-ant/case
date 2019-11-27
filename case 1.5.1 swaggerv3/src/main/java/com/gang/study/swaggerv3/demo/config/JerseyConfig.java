package com.gang.study.swaggerv3.demo.config;

import com.gang.study.swaggerv3.demo.controller.TestController;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
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
@ApplicationPath("/sample")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(TestController.class);
        register(OpenApiResource.class);
    }

}
