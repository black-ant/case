package com.gang.flowable.demo.service;


import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用于学习整个的流程
 */
@Service
public class FlowableStudyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start() {
        logger.info("------> 创建一个 engine 对象 <-------");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        logger.info("------> 获取对应的 taskservice 处理流程 <-------");
        processEngine.getTaskService();
    }
}
