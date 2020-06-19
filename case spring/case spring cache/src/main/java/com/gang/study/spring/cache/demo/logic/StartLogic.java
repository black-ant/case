package com.gang.study.spring.cache.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/6/18 9:35
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DefaultLogic defaultLogic;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        for (int i = 0; i < 10; i++) {
        //            logger.info("------> back {} get 1 :{} <-------", i, defaultLogic.put("1"));
        //            logger.info("------> back {} get 2 :{} <-------", i, defaultLogic.put("2"));
        //            logger.info("------> back {} get 3: {} <-------", i, defaultLogic.put("3"));
        //
        //            if (i % 2 == 0) {
        //                logger.info("------> back {} get 2 :{} <-------", i, defaultLogic.put("2"));
        //            } else if (i % 3 == 0) {
        //                logger.info("------> back {} get 3: {} <-------", i, defaultLogic.put("3"));
        //            }
        //
        //            logger.info("------> back {} get 1 :{} <-------", i, defaultLogic.get("1"));
        //            logger.info("------> back {} get 2 :{} <-------", i, defaultLogic.get("2"));
        //            logger.info("------> back {} get 3: {} <-------", i, defaultLogic.get("3"));
        //        }
    }

    @GetMapping("/get/{key}")
    public Integer get(@PathVariable("key") String key) {
        Integer response = defaultLogic.get(key);
        logger.info("------> back {} get 1 :{} <-------", key, response);
        return response;
    }


    @GetMapping("/put/{key}/{value}")
    public Integer put(@PathVariable("key") String key, @PathVariable("value") Integer value) {
        Integer response = defaultLogic.put(key, value);
        logger.info("------> back {} get 1 :{} <-------", key, response);
        return response;
    }


    @GetMapping("/delete/{key}")
    public Integer delete(@PathVariable("key") String key) {
        defaultLogic.delete(key);
        logger.info("------> back {} get 1 :{} <-------", key, defaultLogic.get(key));
        return defaultLogic.get(key);
    }

    @GetMapping("/deleteall/{key}")
    public Integer deleteall(@PathVariable("key") String key) {
        defaultLogic.deleteAll();
        logger.info("------> back {} get 1 :{} <-------", key, defaultLogic.get(key));
        return defaultLogic.get(key);
    }
}
