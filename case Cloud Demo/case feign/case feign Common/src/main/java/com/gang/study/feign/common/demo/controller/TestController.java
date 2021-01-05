package com.gang.study.feign.common.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.feign.common.demo.mapper.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/4/22 21:03
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

        @Autowired
        private ServiceMapper serviceMapper;

    @GetMapping("get")
    public void run() throws Exception {
        logger.info("------> this is in user <-------");
        JSONObject jsonObject = serviceMapper.get("111");
                logger.info("------> this is {} <-------", jsonObject.toJSONString());
    }
}
