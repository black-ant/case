package com.gang.study.webflow.demo.config;

import com.gang.study.webflow.demo.handler.TestFlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.config.FlowExecutorBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

/**
 * @Classname BuildFlowConfig
 * @Description TODO
 * @Date 2021/1/21 16:17
 * @Created by zengzg
 */
@Configuration
public class BuildFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private ApplicationContext context;

    public FlowDefinitionRegistry flowRegistry() {
        return new FlowDefinitionRegistryBuilder(context)
                .setBasePath("classpath*:/flow")
                .addFlowLocationPattern("/*-flow.xml")
                .build();
    }

    public FlowExecutor flowExecutor() {
        return new FlowExecutorBuilder(flowRegistry()).build();
    }

    @Bean
    public FlowHandlerAdapter getFlowHandlerAdapter() {
        FlowHandlerAdapter adapter = new FlowHandlerAdapter();
        adapter.setFlowExecutor(flowExecutor());
        return adapter;
    }

    /**
     * Step 4 : 添加 Handler Mapping
     *
     * @return
     */
    @Bean
    public HandlerMapping startFlowHandlerMapping() {
        final FlowHandlerMapping handler = new FlowHandlerMapping();
        handler.setOrder(0);
//        handler.setDefaultHandler(new TestFlowHandler());
        handler.setFlowRegistry(flowRegistry());
//        handler.setInterceptors(loginFlowHandlerMappingInterceptors());
        return handler;
    }

}
