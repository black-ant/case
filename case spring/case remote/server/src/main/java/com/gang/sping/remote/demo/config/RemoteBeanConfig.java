package com.gang.sping.remote.demo.config;

import com.gang.sping.remote.demo.service.ITestService;
import com.gang.sping.remote.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;

/**
 * @Classname RemoteBean
 * @Description TODO
 * @Date 2020/10/31 16:54
 * @Created by zengzg
 */
@Configuration
public class RemoteBeanConfig {

    @Autowired
    private TestService testService;


    @Bean(name="/remoteService")
    public HttpInvokerServiceExporter getHttpInvokerServiceExporter() {
        HttpInvokerServiceExporter serviceExporter = new HttpInvokerServiceExporter();
        serviceExporter.setService(testService);
        serviceExporter.setServiceInterface(ITestService.class);
        return serviceExporter;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        HashMap map = new HashMap();
        map.put("/test", getHttpInvokerServiceExporter());
        simpleUrlHandlerMapping.setUrlMap(map);
        return simpleUrlHandlerMapping;
    }
}
