package com.gang.study.cloud.stream.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @Classname Consume
 * @Description TODO
 * @Date 2019/11/25 14:57
 * @Created by zengzg
 */
@Component
public class Consume {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @StreamListener(value = "SyncTest", condition = "headers['type']=='org'&&headers['app-type']=='ding'&&headers['operation']=='create' ")
    //    public void consume(String msg) {
    //        logger.info("------> this is in consume <-------");
    //        logger.info("------> this msg is :{} <-------", msg);
    //    }

    @StreamListener(value = "SyncEventProducer", condition = "headers['type']=='org'&&headers['app-type']=='ding'&&headers['operation']=='create' ")
    public void syncEventProducer(String msg) {
        logger.info("------> this is in consume <-------");
        logger.info("------> this msg is :{} <-------", msg);
    }

    @StreamListener(value = "SyncEventProducer", condition = "headers['type']=='org'&&headers['app-type']=='ding'&&headers['operation']=='create' ")
    public void syncEventConsumer(String msg) {
        logger.info("------> this is in consume <-------");
        logger.info("------> this msg is :{} <-------", msg);
    }

    //    @StreamListener(Sink.INPUT)
    //    public void processVote(String person) {
    //        logger.info("------> Received :{} <-------", person.toString());
    //    }
    //
    //    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='org'&&headers['app-type']=='ding'&&headers['operation']=='create' ")
    //    public void processCic(String person) {
    //        logger.info("------> CIC ---- Received :{} <-------", person.toString());
    //    }
}
