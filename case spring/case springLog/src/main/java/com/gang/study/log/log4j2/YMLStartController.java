package com.gang.study.log.log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/8/27 11:11
 * @Created by zengzg
 */
@RequestMapping("ymllog")
public class YMLStartController {

    @Autowired
    private YMLStartService service;

    @GetMapping("start")
    public String doLog() {
        service.doLog();
        return "success";
    }
}
