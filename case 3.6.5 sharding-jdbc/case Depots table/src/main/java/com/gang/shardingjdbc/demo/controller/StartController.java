package com.gang.shardingjdbc.demo.controller;

import com.gang.shardingjdbc.demo.entity.api.User;
import com.gang.shardingjdbc.demo.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/4/26
 * @Created by zengzg
 */
@RestController
@RequestMapping("start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StartService startService;

    @GetMapping("success")
    public void save() {

        try {
            startService.run();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

}
