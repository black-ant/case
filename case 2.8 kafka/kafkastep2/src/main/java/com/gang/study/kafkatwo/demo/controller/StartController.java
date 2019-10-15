package com.gang.study.kafkatwo.demo.controller;

import com.gang.study.kafkatwo.demo.service.SendMsg;
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
    private SendMsg sendMsg;

    @GetMapping("send")
    public void sendMsg(@RequestParam("type") String type, @RequestParam("msg") String msg, @RequestParam("topic") String topic) {
        switch (type) {
            case "1":
                sendMsg.sendMessage("test");
                break;
            case "2":
                sendMsg.sendMessageHavaTopic(topic, msg);
                break;
            case "3":
                thread(topic, msg);
                break;
            default:

        }

    }

    public void thread(String topic, String msg) {

        for (int i = 0; i < 1; i++) {
            new newThread(topic, msg, String.valueOf(i)).start();
        }
    }

    class newThread extends Thread {

        private String topic;
        private String msg;
        private String num;

        public newThread(String topic, String msg, String num) {
            this.topic = topic;
            this.msg = msg;
            this.num = num;
        }

        @Override
        public void run() {
            for (int x = 0; x < 100; x++) {
                sendMsg.sendMessageHavaTopic("all001", x + "--test--" + num);
            }

        }

    }

    @GetMapping("test")
    public void test() {
        logger.info("in test");
    }
}
