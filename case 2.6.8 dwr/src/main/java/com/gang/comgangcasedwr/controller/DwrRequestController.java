package com.gang.comgangcasedwr.controller;

import com.gang.comgangcasedwr.service.Demo1Service;
import com.gang.comgangcasedwr.service.Demo2Service;
import com.gang.comgangcasedwr.to.TextNotifyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DwrRequestController
 * @Description TODO
 * @Date 2021/4/8
 * @Created by zengzg
 */
@RequestMapping("request")
@RestController
public class DwrRequestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Demo1Service demo1Service;

    @GetMapping("ask")
    public String test() {
        TextNotifyMessage textNotifyMessage = new TextNotifyMessage();
        textNotifyMessage.setData("success web");
        demo1Service.onMessage(textNotifyMessage);
        return "success";
    }
}
