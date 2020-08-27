package com.gang.study.log.date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/8/27 11:11
 * @Created by zengzg
 */
@RequestMapping("datelog")
public class DateStartController {

    @Autowired
    private DateStartService service;

    @GetMapping("start")
    public String doLog() {
        service.doLog();
        return "success";
    }
}
