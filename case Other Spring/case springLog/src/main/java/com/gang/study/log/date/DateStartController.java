package com.gang.study.log.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/8/27 11:11
 * @Created by zengzg
 */
@RequestMapping("datelog")
@RestController
public class DateStartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DateStartService service;

    @GetMapping("start")
    public String doLog() {
        service.doLog();
        return "success";
    }

    @GetMapping("trace")
    public void doLogTrace() {
        try {
            service.doLog();
            service.doLogTrach();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

    }
}
