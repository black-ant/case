package com.gang.study.quartz.demo.logic;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ScheduleJob
 * @Description TODO
 * @Date 2020/2/16 15:46
 * @Created by zengzg
 */
public class ScheduleJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("------> this is in  execute<-------");
    }
}
