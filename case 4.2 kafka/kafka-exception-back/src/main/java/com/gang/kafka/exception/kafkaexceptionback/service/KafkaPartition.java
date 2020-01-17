package com.gang.kafka.exception.kafkaexceptionback.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaPartition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @KafkaListener(topics = "start", groupId = "ant", topicPartitions = {@TopicPartition(topic = "start", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "-1"))})
    public void Listener0(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner 0 :{}<-------", record.value());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @KafkaListener(topics = "start", groupId = "ant", topicPartitions = {@TopicPartition(topic = "start", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "-1"))})
    public void Listener(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner 1:{}<-------", record.value());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @KafkaListener(topics = "start", groupId = "ant", topicPartitions = {@TopicPartition(topic = "start", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "-1"))})
    public void Listener1(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner 2:{}<-------", record.value());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
