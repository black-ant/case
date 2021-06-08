package com.gang.sample.service;

import com.gang.sample.template.ActionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;

/**
 * @Classname SimpleActionCreater
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
@Component
public class SimpleActionCreater implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected FlowDefinitionRegistry definitionRegistry;

    @Autowired
    private ActionTemplate actionTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [手动构建一个 Flow] <-------");
        createSimpleActionFlowDefinition();
    }

    public void createSimpleActionFlowDefinition() {
        FlowDefinition definition = new Flow("simpleAction");
        definitionRegistry.registerFlowDefinition(definition);
    }

    public void create(String id) {
        Flow flow = (Flow) definitionRegistry.getFlowDefinition(id);

        ActionState actionState = new ActionState(flow, "simpleStart");

        if (flow != null) {
//            actionTemplate.createStartState(flow, )
        }
    }

}
