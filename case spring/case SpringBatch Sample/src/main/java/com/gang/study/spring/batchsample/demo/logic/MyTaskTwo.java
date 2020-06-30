package com.gang.study.spring.batchsample.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @Classname MyTaskTwo
 * @Description TODO
 * @Date 2020/6/30 15:43
 * @Created by zengzg
 */
public class MyTaskTwo implements Tasklet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("MyTaskTwo start..");

        logger.info("------> this is in MyTaskTwo execute <-------");

        System.out.println("MyTaskTwo done..");
        return RepeatStatus.FINISHED;
    }
}


