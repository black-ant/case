package com.gang.study.cloud.stream.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import para.cic.sync.common.api.SyncConstants;
import para.cic.sync.common.api.SyncEventProducer;
import para.cic.sync.common.api.TestClass;

/**
 * @Classname Produce
 * @Description TODO
 * @Date 2019/11/25 14:57
 * @Created by zengzg
 */
@Component
public class Produce {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Autowired(required = false)
    //    private SyncProduceTest syncProduceTest;
    //
    //    public void send() {
    //        syncProduceTest.producer().send(MessageBuilder.withPayload("hello " +
    //                "stream").setHeader(SyncConstants.HEADER_OPERATION,
    //                "create").setHeader(SyncConstants.HEADER_TYPE, "org").setHeader(SyncConstants.HEADER_APP_TYPE,
    //                "ding").build());
    //    }

    @Autowired(required = false)
    private SyncEventProducer syncEventProducer;

    @Autowired
    private TestClass testClass;

    public void sendMain() {
        logger.info("------> this is in test :{} <-------", testClass.getName());
        syncEventProducer.producer().send(MessageBuilder.withPayload("hello1 " +
                "stream").setHeader(SyncConstants.HEADER_OPERATION,
                "create").setHeader(SyncConstants.HEADER_TYPE, "org").setHeader(SyncConstants.HEADER_APP_TYPE,
                "ding").build());
    }

}
