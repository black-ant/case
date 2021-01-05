package com.gang.study.commontest.schedule.demo.controller;

import com.gang.common.lib.module.utils.TaskSchedulTypeEnum;
import com.gang.common.lib.module.utils.TaskScheduleModel;
import com.gang.common.lib.utils.CronUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/2/13 22:46
 * @Created by zengzg
 */
@RequestMapping("/test")
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/test")
    public String test() {
        logger.info("------> this is in test <-------");
        return "that's ok";
    }

    @GetMapping("/cron")
    public String get() {
        TaskScheduleModel taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.addTimeInterval(TaskSchedulTypeEnum.SECONDS, "45");
        logger.info("------> {}  <-------", CronUtils.createCronExpression(taskScheduleModel));
        return CronUtils.createCronExpression(taskScheduleModel);
    }


}
