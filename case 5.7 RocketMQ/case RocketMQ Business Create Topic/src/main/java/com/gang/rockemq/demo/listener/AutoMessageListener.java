package com.gang.rockemq.demo.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Classname AutoMessageListener
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
public class AutoMessageListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String topicName;

    public AutoMessageListener(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext context) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs.get(0).getQueueId());

        logger.info("------> {} Receive New Messages:{} - {} <-------", Thread.currentThread().getName(), msgs.get(0).getQueueId(), topicName);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
