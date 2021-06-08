package com.gang.sample.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @Classname SimpleAction
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
public class SimpleAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        logger.info("------> [进入自定义逻辑] <-------");
        return null;
    }
}
