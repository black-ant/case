package com.gang.study.quartz.demo.config;

import com.gang.study.quartz.demo.logic.CronQuartzJobBean;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname QuartzSampleConfig
 * @Description TODO
 * @Date 2021/2/10 14:48
 * @Created by zengzg
 */
@Configuration
public class QuartzSampleConfig {

    // 更新频率 , 每30秒更新一次
    private static final int TIME = 30;

    // JobDetail :  注册任务
    @Bean
    public JobDetail weatherDataSyncJobDetail() {
        return JobBuilder.newJob(CronQuartzJobBean.class).withIdentity("myjob")
                .storeDurably().build();
    }

    // Trigger : 触发器
    @Bean
    public Trigger weatherDataSyncTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTrigger").withSchedule(schedBuilder).build();
    }


}
