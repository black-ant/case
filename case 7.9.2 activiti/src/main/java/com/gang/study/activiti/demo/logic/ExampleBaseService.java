package com.gang.study.activiti.demo.logic;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import javax.annotation.Resource;

/**
 * @Classname ExampleBaseService
 * @Description TODO
 * @Date 2021/2/11 14:45
 * @Created by zengzg
 */
public class ExampleBaseService {

    protected static final String PROCESS_DEFINE_KEY = "vacationProcess";

    @Resource
    protected RuntimeService runtimeService;
    @Resource
    protected IdentityService identityService;
    @Resource
    protected TaskService taskService;
    @Resource
    protected HistoryService historyService;
}
