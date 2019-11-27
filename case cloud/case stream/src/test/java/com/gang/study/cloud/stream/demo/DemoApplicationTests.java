package com.gang.study.cloud.stream.demo;

import com.gang.study.cloud.stream.demo.logic.Produce;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@EnableBinding(value = {SyncProduceTest.class, SyncConsumeTest.class})
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Produce produce;

    //    @Autowired
    //    private SinkSender sinkSender;

    /**
     * sinkSender out : 发送到 output 指定的管道
     *
     * @StreamListener(Sink.INPUT) : 接收对应的管道信息
     */
    @Test
    public void sinkSenderTester() {
        //        sinkSender.output().send(MessageBuilder.withPayload("produce a message ：http://blog.didispace.com").build());
        //        sinkSender.output().send(MessageBuilder.withPayload("produce a message ：http://blog.didispace.com").build());
        //        sinkSender.output().send(MessageBuilder.withPayload("hello " +
        //                "stream").setHeader(SyncConstants.HEADER_OPERATION,
        //                "create").setHeader(SyncConstants.HEADER_TYPE, "org").setHeader(SyncConstants.HEADER_APP_TYPE,
        //                "ding").build());
        sendMyself();
    }

    public void sendMyself() {
        logger.info("------> this is in myself <-------");
        //        produce.send();
        produce.sendMain();
    }

}
