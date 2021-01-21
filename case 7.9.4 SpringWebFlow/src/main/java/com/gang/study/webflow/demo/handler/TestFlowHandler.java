package com.gang.study.webflow.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname TestFlowHandler
 * @Description TODO
 * @Date 2021/1/21 19:04
 * @Created by zengzg
 */
public class TestFlowHandler extends AbstractFlowHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public String handleExecutionOutcome(FlowExecutionOutcome outcome,
                                         HttpServletRequest request, HttpServletResponse response) {

        logger.info("------> this is in TestFlowHandler <-------");
        return "https://www.baidu,com";
    }
}
