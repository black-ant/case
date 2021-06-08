package com.gang.sample.webflow;

import java.util.Collections;

import com.gang.sample.service.DefaultFlowResourceFlowViewResolver;
import com.gang.sample.service.DefaultMvcViewFactoryCreator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.expression.spel.WebFlowSpringELExpressionParser;
import org.springframework.webflow.mvc.builder.FlowResourceFlowViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @see <a href="https://www.baeldung.com/spring-web-flow">Guide to Spring Web Flow</a>
 */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext context;

//    @Autowired
//    private DefaultConversionService conversionService;

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        logger.info("------> 构建映射关系 <-------");
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(flowRegistry());
        return handlerMapping;
    }


    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return new FlowDefinitionRegistryBuilder(context)
                .setFlowBuilderServices(flowBuilderServices())
                .setBasePath("classpath:/flows")
                .addFlowLocation("activation-flow.xml", "activationFlow")
                .build();
    }


//    @Bean
//    public ExpressionParser expressionParser() {
//        return new WebFlowSpringELExpressionParser(new SpelExpressionParser(), conversionService);
//    }


    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry()).build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
//                .setExpressionParser(expressionParser())
                .setViewFactoryCreator(mvcViewFactoryCreator())
                .setDevelopmentMode(true).build();
    }

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        logger.info("------> [构建 View 工厂] <-------");
        MvcViewFactoryCreator factoryCreator = new DefaultMvcViewFactoryCreator();

        // PS : 此句会覆盖下方语句 . 这里感觉设计的有问题
//        factoryCreator.setDefaultViewSuffix(".html");


//        factoryCreator.setViewResolvers(
//                Collections.singletonList(thymeleafViewResolver));
        factoryCreator.setUseSpringBeanBinding(true);
        factoryCreator.setFlowViewResolver(getFlowResourceFlowViewResolver());
        return factoryCreator;
    }

    @Bean
    public DefaultFlowResourceFlowViewResolver getFlowResourceFlowViewResolver() {
        DefaultFlowResourceFlowViewResolver flowResourceFlowViewResolver = new DefaultFlowResourceFlowViewResolver();
        flowResourceFlowViewResolver.setDefaultViewSuffix(".html");
        flowResourceFlowViewResolver.setThymeleafViewResolver(thymeleafViewResolver);
        return flowResourceFlowViewResolver;
    }

}
