package com.gang.simplemq.callbakc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Classname SelfReturnsCallback
 * @Description TODO
 * @Date 2021/4/6
 * @Created by zengzg
 */
public class SelfReturnsCallback implements RabbitTemplate.ReturnsCallback {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void returnedMessage(ReturnedMessage returned) {

    }
}
