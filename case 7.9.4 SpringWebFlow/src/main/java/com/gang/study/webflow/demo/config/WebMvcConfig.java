package com.gang.study.webflow.demo.config;

import com.gang.study.webflow.demo.handler.TestFlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.webflow.config.FlowBuilderServicesBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.DelegatingFlowViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.mvc.view.FlowViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Collections;

/**
 * @Classname WebMvcConfig
 * @Description TODO
 * @Date 2021/1/22 11:59
 * @Created by zengzg
 */
@Configuration
public class WebMvcConfig {

    @Autowired
    private FlowExecutor flowExecutor;
    @Autowired
    private FlowDefinitionRegistry registry;

    @Bean
    public ViewResolver getViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        return thymeleafViewResolver;
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
        handler.setDefaultHandler(new UrlFilenameViewController());
        handler.setFlowRegistry(registry);
        return handler;
    }

    @Bean
    public FlowHandlerAdapter getFlowHandlerAdapter() {
        FlowHandlerAdapter adapter = new FlowHandlerAdapter();
        adapter.setFlowExecutor(flowExecutor);
        return adapter;
    }

//    @Bean
//    public FlowViewResolver flowViewResolver(){
//        return new DelegatingFlowViewResolver()
//    }


    @Bean
    public ViewFactoryCreator viewFactoryCreator() {
        final MvcViewFactoryCreator resolver = new MvcViewFactoryCreator();
        resolver.setViewResolvers(Collections.singletonList(getViewResolver()));
        resolver.setDefaultViewSuffix("html");
        return resolver;
    }

}
