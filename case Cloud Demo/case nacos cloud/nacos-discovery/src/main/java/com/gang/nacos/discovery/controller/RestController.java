package com.gang.nacos.discovery.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname RestController
 * @Description TODO
 * @Date 2020/11/8 11:26
 * @Created by zengzg
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
