package com.gang.rockemq.demo.factory;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname AutoCreateListener
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@Component
public class ListenerFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String namerv = "192.168.158.30:9876;192.168.158.30:9877";

    public void createConsumer(String topicName, String groupName, MessageListenerConcurrently listener) throws Exception {
        //创建消费者，消费组为consumer-A
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        //指定nameserver地址，可以有多个，使用分号分隔
        consumer.setNamesrvAddr(namerv);
        //设置订阅的主题，第二个参数表示该消费者可以消费哪些tag的消息，tag是生产者生产消息时标记的
        //*或者null表示接收所有的tag消息，可以使用“tag1||tag2”过滤消息，而且只支持中间使用||分隔
        consumer.subscribe(topicName, "*");
        //设置监听器，当主题中有消息时，调用监听器消费消息
        consumer.registerMessageListener(listener);
        //启动消费者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
