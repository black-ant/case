package com.gang.sample.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ParserContext;
import org.springframework.binding.expression.spel.SpringELExpressionParser;
import org.springframework.binding.expression.support.FluentParserContext;
import org.springframework.binding.expression.support.LiteralExpression;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.stereotype.Component;
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
 * @Classname ActionTemplate
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
@Component
public class ActionTemplate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected FlowBuilderServices flowBuilderServices;


    /**
     * 设置 Start State
     *
     * @param flow
     * @param state
     */
    public void createStartState(final Flow flow, final String state) {
        flow.setStartState(state);
        final TransitionableState startState = getStartState(flow);
        logger.debug("Start state is now set to [{}]", startState.getId());
    }

    /**
     * 创建一个 View State
     *
     * @param flow
     * @param id
     * @param expression
     * @param binder
     * @return
     */
    public ViewState createViewState(final Flow flow, final String id, final Expression expression,
                                     final BinderConfiguration binder) {
        try {
            if (containsFlowState(flow, id)) {
                logger.debug("Flow [{}] already contains a definition for state id [{}]", flow.getId(), id);
                return (ViewState) flow.getTransitionableState(id);
            }

            final ViewFactory viewFactory = this.flowBuilderServices.getViewFactoryCreator().createViewFactory(
                    expression,
                    this.flowBuilderServices.getExpressionParser(),
                    this.flowBuilderServices.getConversionService(),
                    binder,
                    this.flowBuilderServices.getValidator(),
                    this.flowBuilderServices.getValidationHintResolver());

            final ViewState viewState = new ViewState(flow, id, viewFactory);
            logger.debug("Added view state [{}]", viewState.getId());
            return viewState;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public ActionState createActionState(final Flow flow, final String name, final Action... actions) {
        if (containsFlowState(flow, name)) {
            logger.debug("Flow [{}] already contains a definition for state id [{}]", flow.getId(), name);
            return (ActionState) flow.getTransitionableState(name);
        }
        final ActionState actionState = new ActionState(flow, name);
        logger.debug("Created action state [{}]", actionState.getId());
        actionState.getActionList().addAll(actions);
        logger.debug("Added action to the action state [{}] list of actions: [{}]", actionState.getId(), actionState.getActionList());
        return actionState;
    }


    /**
     * 创建一个 Transaction
     *
     * @param criteriaOutcomeExpression
     * @param targetState
     * @return
     */
    public Transition createTransition(final Expression criteriaOutcomeExpression, final String targetState) {
        final TransitionCriteria criteria;

        if (criteriaOutcomeExpression.toString().equals(WildcardTransitionCriteria.WILDCARD_EVENT_ID)) {
            criteria = WildcardTransitionCriteria.INSTANCE;
        } else {
            criteria = new DefaultTransitionCriteria(criteriaOutcomeExpression);
        }

        final DefaultTargetStateResolver resolver = new DefaultTargetStateResolver(targetState);
        final Transition t = new Transition(criteria, resolver);
        return t;
    }

    /**
     * 创建 Evaluate
     *
     * @param expression
     * @return
     */
    public EvaluateAction createEvaluateAction(final String expression) {
        if (this.flowBuilderServices == null) {
            logger.error("Flow builder services is not configured correctly.");
            return null;
        }
        final ParserContext ctx = new FluentParserContext();
        final Expression action = this.flowBuilderServices.getExpressionParser().parseExpression(expression, ctx);
        final EvaluateAction newAction = new EvaluateAction(action, null);
        logger.debug("Created evaluate action for expression [{}]", action.getExpressionString());
        return newAction;
    }

    public EndState createEndState(final Flow flow, final String id) {
        return createEndState(flow, id, (ViewFactory) null);
    }

    public EndState createEndState(final Flow flow, final String id, final String viewId, final boolean redirect) {
        if (!redirect) {
            return createEndState(flow, id, viewId);
        }
        final Expression expression = createExpression(viewId, String.class);
        final ActionExecutingViewFactory viewFactory = new ActionExecutingViewFactory(new ExternalRedirectAction(expression));
        return createEndState(flow, id, viewFactory);
    }


    public EndState createEndState(final Flow flow, final String id, final String viewId) {
        return createEndState(flow, id, new LiteralExpression(viewId));
    }

    public EndState createEndState(final Flow flow, final String id, final Expression expression) {
        final ViewFactory viewFactory = this.flowBuilderServices.getViewFactoryCreator().createViewFactory(
                expression,
                this.flowBuilderServices.getExpressionParser(),
                this.flowBuilderServices.getConversionService(),
                null,
                this.flowBuilderServices.getValidator(),
                this.flowBuilderServices.getValidationHintResolver());

        return createEndState(flow, id, viewFactory);
    }

    public EndState createEndState(final Flow flow, final String id, final ViewFactory viewFactory) {

        if (containsFlowState(flow, id)) {
            logger.debug("Flow [{}] already contains a definition for state id [{}]", flow.getId(), id);
            return (EndState) flow.getStateInstance(id);
        }

        final EndState endState = new EndState(flow, id);
        if (viewFactory != null) {
            final Action finalResponseAction = new ViewFactoryActionAdapter(viewFactory);
            endState.setFinalResponseAction(finalResponseAction);
            logger.debug("Created end state state [{}] on flow id [{}], backed by view factory [{}]", id, flow.getId(), viewFactory);
        } else {
            logger.debug("Created end state state [{}] on flow id [{}]", id, flow.getId());
        }
        return endState;

    }

    protected boolean containsFlowState(final Flow flow, final String stateId) {
        if (flow == null) {
            logger.error("Flow is not configured correctly and cannot be null.");
            return false;
        }
        return flow.containsState(stateId);
    }

    public TransitionableState getStartState(final Flow flow) {
        return (TransitionableState) flow.getStartState();
    }

    /**
     * 构建 Expression
     *
     * @param expression
     * @param expectedType
     * @return
     */
    protected Expression createExpression(final String expression, final Class expectedType) {
        final ParserContext parserContext = new FluentParserContext()
                .expectResult(expectedType);
        return getSpringExpressionParser().parseExpression(expression, parserContext);
    }


    protected SpringELExpressionParser getSpringExpressionParser() {
        final SpelParserConfiguration configuration = new SpelParserConfiguration();
        final SpelExpressionParser spelExpressionParser = new SpelExpressionParser(configuration);
        final SpringELExpressionParser parser = new SpringELExpressionParser(spelExpressionParser,
                this.flowBuilderServices.getConversionService());

        parser.addPropertyAccessor(new ActionPropertyAccessor());
        parser.addPropertyAccessor(new BeanFactoryPropertyAccessor());
        parser.addPropertyAccessor(new FlowVariablePropertyAccessor());
        parser.addPropertyAccessor(new MapAdaptablePropertyAccessor());
        parser.addPropertyAccessor(new MessageSourcePropertyAccessor());
        parser.addPropertyAccessor(new ScopeSearchingPropertyAccessor());
        parser.addPropertyAccessor(new BeanExpressionContextAccessor());
        parser.addPropertyAccessor(new MapAccessor());
        parser.addPropertyAccessor(new MapAdaptablePropertyAccessor());
        parser.addPropertyAccessor(new EnvironmentAccessor());
        parser.addPropertyAccessor(new ReflectivePropertyAccessor());
        return parser;

    }

    protected void createAction(final Flow flow, String actionId, String checkId) {
        final ActionState terminateSession = createActionState(flow, actionId, createEvaluateAction("simpleAction"));
        createStateDefaultTransition(terminateSession, checkId);
    }

    public Transition createTransition(final String targetState) {
        final DefaultTargetStateResolver resolver = new DefaultTargetStateResolver(targetState);
        return new Transition(resolver);
    }

    protected void createStateDefaultTransition(final TransitionableState state, final String targetState) {
        if (state == null) {
            logger.debug("Cannot add default transition of [{}] to the given state is null and cannot be found in the flow.", targetState);
            return;
        }
        final Transition transition = createTransition(targetState);
        state.getTransitionSet().add(transition);
    }


}
