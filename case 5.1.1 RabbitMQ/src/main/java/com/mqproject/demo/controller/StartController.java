package com.mqproject.demo.controller;


import com.mqproject.demo.config.RabbitConfig;
import com.mqproject.demo.entity.Book;
import com.mqproject.demo.handler.MQProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public StartController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public MQProduce produce;

    @GetMapping
    public void defaultMessage() {
        Book book = new Book();
        book.setId("1");
        book.setName("一起来学Spring Boot");
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book);
        this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book);
    }

    @GetMapping("sendMsg")
    public String sendMsg(@RequestParam("msg")String msg){
        logger.info("正在发送消息-》:{}",msg);
        produce.sendMsg(msg);
        return "ok";
    }

}
