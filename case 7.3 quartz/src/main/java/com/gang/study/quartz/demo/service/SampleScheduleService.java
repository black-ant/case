package com.gang.study.quartz.demo.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname SampleScheduleService
 * @Description Schedule 基本使用
 * @Date 2021/2/10 14:23
 * @Created by zengzg
 */

@Service
public class SampleScheduleService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in SampleScheduleService <-------");
        scheduleTest();
    }

    /**
     * 一个简单的 Schedule 流程
     *
     * @throws Exception
     */
    public void scheduleTest() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // 创建Scheduler实例
        Scheduler scheduler = schedulerFactory.getScheduler();

        //启动
        scheduler.start();
        //暂停
        scheduler.standby();
        //停止
        scheduler.shutdown();
        //添加调度计划
//        scheduler.scheduleJob(JobDetail var1, Trigger var2);
        //立即运行一次作业
//        scheduler.triggerJob(JobKey var1) ;
        // 修改触发器
//        scheduler.rescheduleJob(TriggerKey var1, Trigger var2) ;
        //暂停作业
//        scheduler.pauseJob(JobKey var1);
        //恢复作业
//        scheduler.resumeJob(JobKey var1) ;
        //删除作业
//        scheduler.deleteJob(JobKey var1);
    }
}
