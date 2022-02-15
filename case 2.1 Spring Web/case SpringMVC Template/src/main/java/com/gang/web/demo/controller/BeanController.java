package com.gang.web.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.web.demo.to.DateBeanTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("save")
    public List<DateBeanTO> create(@RequestBody List<DateBeanTO> beanTO) {
        logger.info("------> data info :{} <-------", JSONObject.toJSONString(beanTO));
        beanTO.get(0).setCreateDate(new Date());
        return beanTO;
    }
}
