package com.gang.study.quartzauto.demo.logic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname OtherLogic
 * @Description TODO
 * @Date 2020/4/13 21:12
 * @Created by zengzg
 */
@Component
public class OtherLogic implements Job {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("------> this is in Other logic <-------");
    }
}
