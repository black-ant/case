package com.gang.study.quartz.demo.api;

import com.gang.study.quartz.demo.to.SchedulingPlan;

/**
 * @Classname SchedulerService
 * @Description TODO
 * @Date 2021/2/10 14:51
 * @Created by zengzg
 */
public interface SchedulerService {

    /**
     * 添加调度计划
     *
     * @param schedulingPlan 计划信息
     */
    public int addSchedulingPlan(SchedulingPlan schedulingPlan);

    /**
     * 运行指定计划
     *
     * @param schedulingPlan 计划信息
     */
    public int runSchedulingPlan(SchedulingPlan schedulingPlan);

    /**
     * 修改调度计划
     *
     * @param schedulingPlan 计划信息
     */
    public int upSchedulingPlan(SchedulingPlan schedulingPlan);


    /**
     * 暂停指定调度计划
     *
     * @param schedulingPlan 计划信息
     */
    public int suspendSchedulingPlan(SchedulingPlan schedulingPlan);


    /**
     * 恢复调度计划
     *
     * @param schedulingPlan 计划信息
     */
    public int recoverySchedulingPlan(SchedulingPlan schedulingPlan);

    /**
     * 删除指定调度计划
     *
     * @param schedulingPlan 计划信息
     */
    public int deleteSchedulingPlan(SchedulingPlan schedulingPlan);
}
