package com.gang.cloud.stream.streamrabbit.service;

import com.gang.cloud.stream.streamrabbit.api.StreamRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Classname SendService
 * @Description TODO
 * @Date 2021/2/9 15:12
 * @Created by zengzg
 */
@RestController
public class SendService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StreamRabbitMq streamRabbitMq;

    @GetMapping("/send")
    public void process() {
        logger.info("------> this is in send <-------");
        streamRabbitMq.output().send(MessageBuilder.withPayload("now 1: " + new Date()).build());
    }

    @GetMapping("/send2")
    public void process2() {
        logger.info("------> this is in send2 <-------");
        streamRabbitMq.output2().send(MessageBuilder.withPayload("now 2 : " + new Date()).build());
    }

    @GetMapping("/send3")
    public void process3() {
        logger.info("------> this is in send2 <-------");
        streamRabbitMq.output2().send(MessageBuilder.withPayload("now 3 : " + new Date()).build());
    }
}
