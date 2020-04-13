package com.gang.study.quartzauto.demo.controller;

import com.gang.study.quartzauto.demo.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/4/13 20:44
 * @Created by zengzg
 */
@RestController
@RequestMapping(value = "/test")
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskController taskController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCronExpression("0/10 * * * * ? *");
        taskEntity.setJobClass("com.gang.study.quartzauto.demo.logic.TestLogic");
        taskEntity.setJobGroup("gang");
        taskEntity.setJobName("test");
        taskEntity.setJobDescription("this is application id");
        taskController.addTask(taskEntity);
    }

    @GetMapping("/run")
    public String run() {
        logger.info("------> this is in Get run <-------");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCronExpression("0/10 * * * * ? *");
        taskEntity.setJobClass("com.gang.study.quartzauto.demo.logic.OtherLogic");
        taskEntity.setJobGroup("gang");
        taskEntity.setJobName("testget");
        taskEntity.setJobDescription("this is application id");
        taskController.addTask(taskEntity);
        return "ok";
    }


    @GetMapping("/delete")
    public String delete() {
        logger.info("------> this is in delete run <-------");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobGroup("gang");
        taskEntity.setJobName("testget");
        taskController.deleteTask(taskEntity);
        return "ok";
    }
}
