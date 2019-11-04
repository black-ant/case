package com.gang.study.taskkafka.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @Classname KafkaListener
 * @Description TODO
 * @Date 2019/11/1 13:45
 * @Created by ant-black 1016930479@qq.com
 */

@Service
public class KafkaListenerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @KafkaListener(id = "foo", topics = "myTopic", clientIdPrefix = "myClientId")
     */
    @KafkaListener(id = "one", topics = "start", clientIdPrefix = "myClientId")
    public void listener0(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner 0:{}<-------", record.value());
    }

    //    @KafkaListener(topics = "start", groupId = "ant", topicPartitions = {
//            @TopicPartition(topic = "start", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "1"))})
    public void Listener(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner :{}<-------", record.value());
    }

    //    @KafkaListener(topics = "start", groupId = "ant", topicPartitions = {
//            @TopicPartition(topic = "start", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "1"))})
    public void Listener2(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner :{}<-------", record.value());
    }

    /**
     * 1 可以通过 @Head 获取特殊属性
     *
     * @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
     * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
     * @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
     * @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
     * <p>
     * partition : 分区 ，设置为 0 会接收所有分区的
     * initialOffset : 初始偏移量 -- 是否接收其他分区的任务
     */

//    @KafkaListener(topics = "test", groupId = "ant", topicPartitions = {
//            @TopicPartition(topic = "test", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "-1"))})
    public void Listener2(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Object key,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        logger.info("------> this is in listerner :{} -- partiton :{}-- topic :{} -- ts :{}<-------", key, partition, topic, ts);
    }

}
