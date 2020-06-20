package com.gang.study.engine.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.engine.demo.logic.BeanContainerExample;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname BeanExampleController
 * @Description TODO
 * @Date 2020/6/20 15:42
 * @Created by zengzg
 */
@RequestMapping("/bean")
@RestController
public class BeanExampleController {

    @Autowired
    private BeanContainerExample beanContainerExample;

    @GetMapping("get")
    public String getBean() {
        JSONObject response = new JSONObject();
        response.put("produce", beanContainerExample.get("produce"));
        response.put("comsume", beanContainerExample.get("comsume"));
        return response.toJSONString();
    }
}
