package com.gang.flowable.demo.service.callback;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FlowableCallback implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DelegateExecution execution) {
        logger.info("isok ------> " + "Calling the external system for employee "
                + execution.getVariable("employee") + " <-------");
    }
}
