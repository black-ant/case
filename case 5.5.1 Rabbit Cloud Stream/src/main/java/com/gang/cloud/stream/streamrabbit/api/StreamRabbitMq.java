package com.gang.cloud.stream.streamrabbit.api;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Classname StreamRabbitMq
 * @Description TODO
 * @Date 2021/2/9 15:11
 * @Created by zengzg
 */
public interface StreamRabbitMq {

    String INPUT1_NAME = "input1";
    String INPUT2_NAME = "input2";
    String OUTPUT_NAME = "output1";
    String OUTPUT2_NAME = "output2";

    @Input(StreamRabbitMq.INPUT1_NAME)
    SubscribableChannel input();

    @Input(StreamRabbitMq.INPUT2_NAME)
    SubscribableChannel input2();

    @Output(StreamRabbitMq.OUTPUT_NAME)
    MessageChannel output();

    @Output(StreamRabbitMq.OUTPUT2_NAME)
    MessageChannel output2();
}
