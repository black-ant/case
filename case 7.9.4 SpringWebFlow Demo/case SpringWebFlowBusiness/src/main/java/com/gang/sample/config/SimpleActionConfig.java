package com.gang.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ParserContext;
import org.springframework.binding.expression.spel.SpringELExpressionParser;
import org.springframework.binding.expression.support.FluentParserContext;
import org.springframework.binding.expression.support.LiteralExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.webflow.action.EvaluateAction;
import org.springframework.webflow.action.ExternalRedirectAction;
import org.springframework.webflow.action.ViewFactoryActionAdapter;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.TransitionCriteria;
import org.springframework.webflow.engine.TransitionableState;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.WildcardTransitionCriteria;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.engine.support.ActionExecutingViewFactory;
import org.springframework.webflow.engine.support.DefaultTargetStateResolver;
import org.springframework.webflow.engine.support.DefaultTransitionCriteria;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.expression.spel.ActionPropertyAccessor;
import org.springframework.webflow.expression.spel.BeanFactoryPropertyAccessor;
import org.springframework.webflow.expression.spel.FlowVariablePropertyAccessor;
import org.springframework.webflow.expression.spel.MapAdaptablePropertyAccessor;
import org.springframework.webflow.expression.spel.MessageSourcePropertyAccessor;
import org.springframework.webflow.expression.spel.ScopeSearchingPropertyAccessor;

/**
 * @Classname SimpleActionConfig
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
@Configuration
public class SimpleActionConfig {




}
