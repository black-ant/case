package com.gang.study.webflow.demo.config;

import com.gang.study.webflow.demo.conversion.DefaultConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.config.FlowBuilderServicesBuilder;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.config.FlowExecutorBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.expression.spel.WebFlowSpringELExpressionParser;

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
    @Autowired
    private ViewFactoryCreator viewFactoryCreator;
    @Autowired
    private DefaultConversionService conversionService;

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return new FlowDefinitionRegistryBuilder(context)
                .setBasePath("classpath:/flow")
                .addFlowLocation("login-flow.xml", "login")
                .addFlowLocation("test-flow.xml", "test")
                .build();
    }

    @Bean
    public FlowExecutor flowExecutor() {
        return new FlowExecutorBuilder(flowRegistry()).build();
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new WebFlowSpringELExpressionParser(new SpelExpressionParser(), conversionService);
    }


    @Bean
    public FlowBuilderServices builder() {
        final FlowBuilderServicesBuilder builder = new FlowBuilderServicesBuilder(context);
        builder.setViewFactoryCreator(viewFactoryCreator);
        builder.setExpressionParser(expressionParser());
        builder.setDevelopmentMode(Boolean.TRUE);
        return builder.build();
    }
}
