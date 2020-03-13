package com.gang.flowable.demo.controller;

import com.gang.flowable.demo.service.FlowableRunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunFlowable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlowableRunService flowableRunService;


    /**
     * flowable 流程
     */
    @GetMapping("start")
    public void startFlowable() {

        logger.info("------> this is in  startFlowable<-------");
        flowableRunService.start();
    }
}
