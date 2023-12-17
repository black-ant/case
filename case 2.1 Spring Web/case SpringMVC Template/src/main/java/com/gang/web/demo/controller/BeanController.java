package com.gang.web.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.web.demo.service.UserService;
import com.gang.web.demo.to.DateBeanTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Classname BeanController
 * @Description TODO
 * @Date 2021/11/2
 * @Created by zengzg
 */
@RequestMapping("/bean")
@RestController
public class BeanController {

    private static Logger logger = LoggerFactory.getLogger(BeanController.class);

    @Resource
    private UserService userService;

//    @PostMapping("save")
//    public List<DateBeanTO> create(@RequestBody List<DateBeanTO> beanTO) {
//        logger.info("------> data info :{} <-------", JSONObject.toJSONString(beanTO));
//        beanTO.get(0).setCreateDate(new Date());
//        return beanTO;
//    }

    @GetMapping("user")
    private String testUser(){
        return userService.test();
    }
}
