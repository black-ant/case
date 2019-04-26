package com.mykafka.demo.controller;

import com.mykafka.demo.service.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 17:14
 * @Version 1.0
 **/
@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SendMsg sendMsg;

    @GetMapping("send")
    public void sendMsg(@RequestParam("type") String type, @RequestParam("msg") String msg, @RequestParam("topic") String topic) {
        switch (type) {
            case "1":
                sendMsg.sendMessage("test");
                break;
            case "2":
                sendMsg.sendMessageHavaTopic(topic, msg);
                break;
            default:

        }

    }

    @GetMapping("test")
    public void test() {
        logger.info("in test");
    }
}
