package com.gang.study.webflow.demo.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.awt.event.ActionEvent;

/**
 * @Classname LoginAction
 * @Description TODO
 * @Date 2021/1/21 14:26
 * @Created by zengzg
 */
@Component
public class LoginAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Event doExecute(RequestContext context) throws Exception {
        logger.info("------> this is in loginAction <-------");
        return super.success();
    }

}
