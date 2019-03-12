package com.mycache.encache.controller;

import com.mycache.encache.entity.Salaries;
import com.mycache.encache.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/8 21:37
 * @Version 1.0
 **/
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private List<Salaries> slist = new LinkedList<Salaries>();

    @Autowired
    TestService testService;

    @GetMapping("map")
    public String LomToMap() {
        slist = testService.findAll();
        oldHandle();
        newHandle();
        return "ok";
    }

    /**
     * before Java 8
     */
    public void oldHandle() {
        long startTime = System.currentTimeMillis();
        long size = slist.size();
        long endTime = System.currentTimeMillis();
        logger.info("start Time :{} ", startTime);
        logger.info("end Time :{} ", endTime);
        logger.info(" Time :{}--param:{} ", endTime - startTime, size);
    }

    /**
     * Java 8 stream
     */
    public void newHandle() {
        long startTime = System.currentTimeMillis();
        long size = slist.stream().count();
        long endTime = System.currentTimeMillis();
        logger.info("start Time :{} ", startTime);
        logger.info("end Time :{} ", endTime);
        logger.info(" Time :{}--param:{}  ", endTime - startTime, size);
    }

}
