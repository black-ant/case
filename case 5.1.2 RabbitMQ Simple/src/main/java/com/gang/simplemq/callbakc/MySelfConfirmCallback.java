package com.gang.simplemq.callbakc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Classname MySelfConfirmCallback
 * @Description TODO
 * @Date 2021/4/6
 * @Created by zengzg
 */
public class MySelfConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("------> CorrelationData is :{} <-------", correlationData);
    }
}
