package com.gang.study.quartz.demo.controller;

import com.gang.study.quartz.demo.api.SchedulerService;
import com.gang.study.quartz.demo.to.SchedulingPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ManagerController
 * @Description TODO
 * @Date 2021/2/10 14:57
 * @Created by zengzg
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    public SchedulerService schedulerService;

    @GetMapping("/addSchedulingPlan")
    public Map<String,Object> addSchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.addSchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }

    @GetMapping("/runSchedulingPlan")
    public Map<String,Object> runSchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.runSchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }


    @GetMapping("/upSchedulingPlan")
    public Map<String,Object> upSchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.upSchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }

    @GetMapping("/suspendSchedulingPlan")
    public Map<String,Object> suspendSchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.suspendSchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }

    @GetMapping("/recoverySchedulingPlan")
    public Map<String,Object> recoverySchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.recoverySchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }

    @GetMapping("/deleteSchedulingPlan")
    public Map<String,Object> deleteSchedulingPlan(SchedulingPlan schedulingPlan) {
        int i = schedulerService.deleteSchedulingPlan(schedulingPlan);
        Map<String,Object> map = new HashMap<>();
        map.put("code",i);
        if (i == 200) {
            map.put("message","操作成功");
        } else {
            map.put("message","操作失败");
        }
        return map;
    }
}
