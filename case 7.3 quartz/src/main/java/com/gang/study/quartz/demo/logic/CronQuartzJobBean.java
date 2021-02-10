package com.gang.study.quartz.demo.logic;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Classname CronQuartzJobBean
 * @Description TODO
 * @Date 2020/2/16 15:49
 * @Created by zengzg
 */
@Component
public class CronQuartzJobBean extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("------> EA0001 CronQuartzJobBean :{}<-------", new Date().getSeconds());
    }

}
