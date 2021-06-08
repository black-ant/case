package com.gang.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.binding.expression.beanwrapper.BeanWrapperExpressionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.mvc.builder.FlowResourceFlowViewResolver;
import org.springframework.webflow.mvc.view.AbstractMvcViewFactory;
import org.springframework.webflow.validation.ValidationHintResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Locale;

/**
 * @Classname DefaultFlowResourceFlowViewResolver
 * @Description TODO
 * @Date 2021/6/6
 * @Created by zengzg
 */
public class DefaultFlowResourceFlowViewResolver extends FlowResourceFlowViewResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String defaultViewSuffix = ".html";

    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public View resolveView(String viewId, RequestContext context) {
        return getViewInternal(viewId, context.getActiveFlow().getApplicationContext());
    }

    private View getViewInternal(String viewPath, ApplicationContext flowContext) {
        logger.info("------> 进行定制处理 <-------");
        viewPath = viewPath.replace(".jsp", "");
        View view = null;
        try {
            view = thymeleafViewResolver.resolveViewName(viewPath, Locale.CANADA);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            //TODO : 待完善
        }
        return view;
    }

    public ThymeleafViewResolver getThymeleafViewResolver() {
        return thymeleafViewResolver;
    }

    public void setThymeleafViewResolver(ThymeleafViewResolver thymeleafViewResolver) {
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    public String getDefaultViewSuffix() {
        return defaultViewSuffix;
    }

    public void setDefaultViewSuffix(String defaultViewSuffix) {
        this.defaultViewSuffix = defaultViewSuffix;
    }

}
