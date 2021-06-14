package com.gang.acttivity.controller;

//import com.gang.acttivity.config.SecurityUtil;

import com.gang.acttivity.config.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessRuntime processRuntime; //实现流程定义相关操作

    @Autowired
    private TaskRuntime taskRuntime; //实现任务相关操作

    @Autowired
    private SecurityUtil securityUtil;//SpringSecurity相关的工具类

    @RequestMapping("/test")
    public String test() {
        logger.info("------> [成功进入 StartController] <-------");
        return "Success !";
    }

    @GetMapping("/info")
    public String getInfd() {
        Page<ProcessDefinition> processDefinitionPage = processRuntime
                .processDefinitions(Pageable.of(0, 10));
        logger.info("------> 可用的流程定义数量：[{}] <-------", processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            logger.info("------> 流程定义：[{}] <-------", pd);
        }

        return "success";
    }

    @GetMapping("/startFlow")
    public String startFlow() {
        securityUtil.logInAs("root");
        ProcessInstance pi = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("SimpleProcess")
                .build());//启动流程实例

        logger.info("------>流程实例ID： + [{}] <-------", pi.getId());
        return "开启流程";
    }

    @GetMapping("/selectFlow")
    public String selectFlow() {
        securityUtil.logInAs("root");
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage.getTotalItems() > 0) {
            logger.info("------> 剩余任务 :[{}] <-------", taskPage.getContent());
        } else {
            logger.info("------> 任务全部执行完成 <-------", taskPage.getContent());
        }
        return "查询 Flow : " + taskPage.getTotalItems();
    }

    @GetMapping("/doFlow")
    public String doFlowBusiness() {

        logger.info("------> [进入 doFlowBusiness 处理流程]  <-------");
        securityUtil.logInAs("root");
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));

        logger.info("------> Task 启动完成 <-------");

        if (taskPage.getTotalItems() > 0) {
            for (Task task : taskPage.getContent()) {

                logger.info("------> 循环处理任务 [{}] <-------", task.getName());

                //拾取任务
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());

                //执行任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }

        logger.info("------> 查询任务的结果 <-------");

        Page<Task> taskPage2 = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage2.getTotalItems() > 0) {
            logger.info("------> 剩余任务 :[{}] <-------", taskPage2.getContent());
        } else {
            logger.info("------> 任务全部执行完成 <-------", taskPage2.getContent());
        }

        return "Success : Do Flow Business 处理完成";
    }


}
