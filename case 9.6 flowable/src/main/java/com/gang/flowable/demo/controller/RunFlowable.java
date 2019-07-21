package com.gang.flowable.demo.controller;

import com.gang.flowable.demo.service.FlowableRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunFlowable {

    @Autowired
    public FlowableRunService flowableRunService;

    @GetMapping("start")
    public void startFlowable() {
        flowableRunService.start();
    }
}
