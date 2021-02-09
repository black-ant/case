package com.gang.cloud.stream.streamrabbit.service;

import com.gang.cloud.stream.streamrabbit.api.StreamRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Classname StreamReceive
 * @Description TODO
 * @Date 2021/2/9 15:12
 * @Created by zengzg
 */
@Component
@EnableBinding(StreamRabbitMq.class)
public class StreamReceive {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * StreamListener 用于监听
     * SendTo 用于广播
     *
     * @param message
     * @return
     */
    @StreamListener(value = StreamRabbitMq.OUTPUT_NAME)
    @SendTo(value = StreamRabbitMq.INPUT1_NAME)
    public String processByStreamInput1(String message) {
        logger.info("------> this is in  processByStreamInput1 :{}<-------", message);
        return "send input2";
    }

    @StreamListener(value = StreamRabbitMq.OUTPUT2_NAME)
    @SendTo(value = StreamRabbitMq.INPUT2_NAME)
    public String processByStreamInput3(String message) {
        logger.info("------> this is in   processByStreamInput3 :{}<-------", message);
        return "send input2";
    }

//    @StreamListener(value = StreamRabbitMq.OUTPUT2_NAME)
//    @SendTo(value = StreamRabbitMq.INPUT2_NAME)
//    public String processByStreamInput5(String message) {
//        logger.info("------> this is in  processByStreamInput4 :{}<-------", message);
//        return "send input2";
//    }


    @StreamListener(value = StreamRabbitMq.INPUT1_NAME)
    public void processByStreamInput2(String message) {
        logger.info("------> this is in  processByStreamInput2 :{}<-------", message);
    }

    @StreamListener(value = StreamRabbitMq.INPUT2_NAME)
    public void processByStreamInput4(String message) {
        logger.info("------> this is in  processByStreamInput4 :{}<-------", message);
    }
}
