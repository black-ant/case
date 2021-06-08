package com.gang.sample.service;

import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.binding.expression.beanwrapper.BeanWrapperExpressionParser;
import org.springframework.util.StringUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.mvc.builder.DelegatingFlowViewResolver;
import org.springframework.webflow.mvc.builder.FlowResourceFlowViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.ServletMvcViewFactory;
import org.springframework.webflow.mvc.view.AbstractMvcViewFactory;
import org.springframework.webflow.mvc.view.FlowViewResolver;
import org.springframework.webflow.validation.ValidationHintResolver;
import org.springframework.webflow.validation.WebFlowMessageCodesResolver;

import java.util.List;

/**
 * @Classname DefaultMvcViewFactoryCreator
 * @Description TODO
 * @Date 2021/6/6
 * @Created by zengzg
 */
public class DefaultMvcViewFactoryCreator extends MvcViewFactoryCreator {

    private FlowViewResolver flowViewResolver = new DefaultFlowResourceFlowViewResolver();

    private boolean useSpringBeanBinding;

    private String eventIdParameterName;

    private String fieldMarkerPrefix;

    private MessageCodesResolver messageCodesResolver = new WebFlowMessageCodesResolver();

    /**
     * Create a new Spring MVC View Factory Creator.
     * @see #setDefaultViewSuffix(String)
     * @see #setEventIdParameterName(String)
     * @see #setFieldMarkerPrefix(String)
     * @see #setUseSpringBeanBinding(boolean)
     * @see #setFlowViewResolver(FlowViewResolver)
     * @see #setViewResolvers(List)
     * @see #setMessageCodesResolver(MessageCodesResolver)
     */
    public DefaultMvcViewFactoryCreator() {

    }

    public void setDefaultViewSuffix(String defaultViewSuffix) {
        FlowResourceFlowViewResolver internalResourceResolver = new FlowResourceFlowViewResolver();
        internalResourceResolver.setDefaultViewSuffix(defaultViewSuffix);
        this.flowViewResolver = internalResourceResolver;
    }

    public void setEventIdParameterName(String eventIdParameterName) {
        this.eventIdParameterName = eventIdParameterName;
    }

    public void setFieldMarkerPrefix(String fieldMarkerPrefix) {
        this.fieldMarkerPrefix = fieldMarkerPrefix;
    }

    public void setUseSpringBeanBinding(boolean useSpringBeanBinding) {
        this.useSpringBeanBinding = useSpringBeanBinding;
    }

    public ViewFactory createViewFactory(Expression viewId, ExpressionParser expressionParser,
                                         ConversionService conversionService, BinderConfiguration binderConfiguration,
                                         Validator validator, ValidationHintResolver validationHintResolver) {
        if (useSpringBeanBinding) {
            expressionParser = new BeanWrapperExpressionParser(conversionService);
        }
        AbstractMvcViewFactory viewFactory = createMvcViewFactoryCustomer(viewId, expressionParser, conversionService,
                binderConfiguration);
        if (StringUtils.hasText(eventIdParameterName)) {
            viewFactory.setEventIdParameterName(eventIdParameterName);
        }
        if (StringUtils.hasText(fieldMarkerPrefix)) {
            viewFactory.setFieldMarkerPrefix(fieldMarkerPrefix);
        }
        viewFactory.setValidator(validator);
        viewFactory.setValidationHintResolver(validationHintResolver);
        return viewFactory;
    }


    /**
     * Creates a concrete instance of an AbstractMvcViewFactory.
     */
    protected AbstractMvcViewFactory createMvcViewFactoryCustomer(Expression viewId, ExpressionParser expressionParser,
                                                          ConversionService conversionService, BinderConfiguration binderConfiguration) {

        return new ServletMvcViewFactory(viewId, flowViewResolver, expressionParser, conversionService,
                binderConfiguration, messageCodesResolver);
    }

    public FlowViewResolver getFlowViewResolver() {
        return flowViewResolver;
    }

    @Override
    public void setFlowViewResolver(FlowViewResolver flowViewResolver) {
        this.flowViewResolver = flowViewResolver;
    }
}
