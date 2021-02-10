package com.gang.study.quartz.demo.logic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname CustomerJob
 * @Description TODO
 * @Date 2021/2/10 14:55
 * @Created by zengzg
 */
public class CustomerJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("定时任务开始执行,作业名=" + jobExecutionContext.getMergedJobDataMap().get("key"));
    }
}
