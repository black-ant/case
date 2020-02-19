package com.gang.study.quartz.demo.config;

import com.gang.study.quartz.demo.logic.CronQuartzJobBean;
import com.gang.study.quartz.demo.logic.ScheduleJob;
import com.gang.study.quartz.demo.logic.TestJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

/**
 * @Classname QuartzConfig
 * @Description TODO
 * @Date 2020/2/16 15:30
 * @Created by zengzg
 */
@Configuration
public class QuartzConfig {

    private static final int TIME = 2; // 更新频率

    // JobDetail
    @Bean
    public JobDetail weatherDataSyncJobDetail() {
        return JobBuilder.newJob(CronQuartzJobBean.class).withIdentity("myjob")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger weatherDataSyncTrigger() {

        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();

        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTrigger").withSchedule(schedBuilder).build();
    }


    //
    //    @Bean
    //    public JobDetailFactoryBean getJobDetailFactoryBean() {
    //        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
    //        jobDetailFactoryBean.setGroup("gang_group");
    //        jobDetailFactoryBean.setJobClass(ScheduleJob.class);
    //        jobDetailFactoryBean.setDurability(Boolean.TRUE);
    //        jobDetailFactoryBean.setApplicationContextJobDataKey("applicationContext");
    //        return jobDetailFactoryBean;
    //    }
    //
    //    @Bean
    //    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
    //        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
    //        cronTriggerFactoryBean.setName("test_trigger");
    //        cronTriggerFactoryBean.setGroup("test_trigger_group");
    //        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
    //        cronTriggerFactoryBean.setCronExpression("0/1 * * * * ?");
    //        return cronTriggerFactoryBean;
    //    }
    //
    //    // 使用jobDetail包装job
    //    @Bean
    //    public JobDetail myJobDetail() {
    //        return JobBuilder.newJob(TestJob.class).withIdentity("myJob").storeDurably().build();
    //    }
    //
    //
    //
    //    // 把jobDetail注册到trigger上去
    //    @Bean
    //    public Trigger myJobTrigger() {
    //        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
    //                .withIntervalInSeconds(15).repeatForever();
    //
    //        return TriggerBuilder.newTrigger()
    //                .forJob(myJobDetail())
    //                .withIdentity("myJobTrigger")
    //                .withSchedule(scheduleBuilder)
    //                .build();
    //    }
    //
    //    // 使用jobDetail包装job
    //    @Bean
    //    public JobDetail myCronJobDetail() {
    //        return JobBuilder.newJob(CronQuartzJobBean.class).withIdentity("myCronJob").storeDurably().build();
    //    }
    //
    //    // 把jobDetail注册到Cron表达式的trigger上去
    //    @Bean
    //    public Trigger CronJobTrigger() {
    //        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
    //
    //        return TriggerBuilder.newTrigger()
    //                .forJob(myCronJobDetail())
    //                .withIdentity("myCronJobTrigger")
    //                .withSchedule(cronScheduleBuilder)
    //                .build();
    //    }


}
