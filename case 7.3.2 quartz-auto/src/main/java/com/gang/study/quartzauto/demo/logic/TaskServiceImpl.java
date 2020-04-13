package com.gang.study.quartzauto.demo.logic;

import com.gang.study.quartzauto.demo.entity.TaskEntity;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Classname TaskServiceImpl
 * @Description TODO
 * @Date 2020/4/13 19:25
 * @Created by zengzg
 */
@Service
public class TaskServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Scheduler scheduler;


    /**
     * @param info
     * @return
     * @// TODO: 2018/6/8 保存定时任务
     */
    @SuppressWarnings("unchecked")
    public Boolean addTask(TaskEntity info) {
        String jobName = info.getJobName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription();
        try {
            if (checkExists(jobName, jobGroup)) {
                //                throw new WelendException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}",
                //                jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder scheduleBuilder =
                    CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger =
                    TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(scheduleBuilder).build();

            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(info.getJobClass());
            JobDetail jobDetail =
                    JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException | ClassNotFoundException e) {
            //            throw new WelendException("类名不存在或执行表达式错误");
        }
        return null;
    }

    /**
     * @param taskEntity
     * @return
     * @// TODO: 2018/6/8 开始定时任务
     */
    public Boolean resumeTask(TaskEntity taskEntity) {
        try {
            scheduler.resumeJob(JobKey.jobKey(taskEntity.getJobName(), taskEntity.getJobGroup()));
            return true;
        } catch (Exception e) {
            //            throw new WelendException(StatusCode.FAILED);
        }
        return null;
    }

    /**
     * @return
     * @// TODO: 2018/6/5  查询job
     */
    public List<TaskEntity> findTaskList(TaskEntity taskEntity) {
        //        taskDao.findTaskList(taskEntity);
        return null;
    }

    /**
     * 修改定时任务
     *
     * @param info
     */
    public Boolean updateTask(TaskEntity info) {
        String jobName = info.getJobName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription(),
                createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {
            if (!checkExists(jobName, jobGroup)) {
                //                throw new WelendException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}",
                //                jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder =
                    CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger =
                    TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
            return true;
        } catch (SchedulerException e) {
            logger.info("------> 类名不存在或执行表达式错误 <-------");
        }
        return null;
    }

    /**
     * @param taskEntity
     * @// TODO: 2018/6/1 停止任务
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean pauseTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param taskEntity
     * @return
     * @// TODO: 2018/6/7 删除任务
     */
    public Boolean deleteTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
                scheduler.unscheduleJob(triggerKey); //移除触发器
                return true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证是否存在
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }
}
