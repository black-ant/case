package com.gang.acttivity.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.acttivity.config.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.activiti.api.task.model.payloads.ReleaseTaskPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @Classname ActivitiProcessRuntimeService
 * @Description TODO
 * @Date 2021/6/17
 * @Created by zengzg
 */
@Component
public class ActivitiProcessRuntimeService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String processDefinitionId = "MyTask001";

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        logger.info("------> [开启一个完整的 Activiti 流程] , 首先模拟登录一个用户 <-------");
//
//        // PS : Activiti 默认依赖 Spring Security
//        securityUtil.logInAs("root");
//
//        createTask();
//
//        selectTaskInfo();
//
//        doTask();
//
//        selectTaskInfo();
    }

    /**
     * 查询当前的 Task 案例
     */
    public void selectTaskInfo() {

        // Step 2 : 查询单个 ProcessDefinition 信息
        ProcessDefinition processDefinition = processRuntime.processDefinition(processDefinitionId);
        logger.info("------> Step 2  查询 ID : {} - 对应的 processDefinition :{} <-------", processDefinitionId, JSONObject.toJSONString(processDefinition));

        // Step 3 : 查询已知的所有的 ProcessDefinition 信息
        Page<ProcessDefinition> processDefinitionPage = processRuntime
                .processDefinitions(Pageable.of(0, 10));
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            logger.info("------> Step 2   Process definition: " + pd);
        }
    }


    /**
     * 创建一个 Task 任务
     */
    public void createTask() {
        logger.info("------> Step 1 : 创建一个 ProcessInstance 开始 <-------");
        securityUtil.logInAs("system");
        String content = pickRandomString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        logger.info("> Processing content: " + content
                + " at " + formatter.format(new Date()));
        ProcessInstance processInstance = processRuntime
                .start(ProcessPayloadBuilder
                        .start()
                        .withProcessDefinitionKey("categorizeProcess")
                        .withProcessInstanceName("Processing Content: " + content)
                        .withVariable("content", content)
                        .build());

        logger.info("------> Step 1 创建一个 ProcessInstance 完成 :{} <-------", JSONObject.toJSONString(processInstance));
        this.processDefinitionId = processInstance.getId();
    }

    /**
     * 执行一个 Task
     */
    public void doTask() {

//        logger.info("------> Step 3-1 : 声明一个 Task 开始 claimed <-------");
//        ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
//        claimTaskPayload.setTaskId(processDefinitionId);
//        processRuntime.claim(claimTaskPayload);
//
//        logger.info("------> Step 3-2 : 发布一个 Task 开始 release <-------");
//        ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
//        releaseTaskPayload.setTaskId(processDefinitionId);
//        processRuntime.release(releaseTaskPayload);
//
//        logger.info("------> Step 3-3 : 完成一个 Task 开始 complete <-------");
//        CompleteTaskPayload payload = new CompleteTaskPayload();
//        payload.setTaskId(processDefinitionId);
//        payload.setVariables(new HashMap<>());
//        processRuntime.complete(payload);
    }


    private String pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return texts[new Random().nextInt(texts.length)];
    }
}
