package com.gang.study.jerseyspringboot.demo;

import com.gang.study.jerseyspringboot.demo.service.TestService;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @Classname JerseyApplication
 * @Description TODO
 * @Date 2020/4/15 23:35
 * @Created by zengzg
 */
@ApplicationPath("/rest")
public class JerseyApplication extends ResourceConfig {

    public JerseyApplication(){
        //packages("com.example.study");
        register(TestService.class);
    }
}
