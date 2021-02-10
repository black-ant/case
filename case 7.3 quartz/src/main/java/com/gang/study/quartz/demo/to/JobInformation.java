package com.gang.study.quartz.demo.to;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**
 * @Classname JobInformation
 * @Description TODO
 * @Date 2021/2/10 14:52
 * @Created by zengzg
 */
public class JobInformation {

    /**
     * Job key
     */
    private JobKey jobKey;
    /**
     * Job 详情
     */
    private JobDetail jobDetail;
    /**
     * Job key
     */
    private TriggerKey triggerKey;
    /**
     * Cron触发器
     */
    private CronTrigger trigger;
    /**
     * code 用于验证是否缺失
     */
    private Integer code;
    /**
     *
     */
    private String message;

    public JobKey getJobKey() {
        return jobKey;
    }

    public void setJobKey(JobKey jobKey) {
        this.jobKey = jobKey;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public TriggerKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(TriggerKey triggerKey) {
        this.triggerKey = triggerKey;
    }

    public CronTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(CronTrigger trigger) {
        this.trigger = trigger;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
