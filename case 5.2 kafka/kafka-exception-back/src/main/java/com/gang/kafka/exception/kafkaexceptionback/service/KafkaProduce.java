package com.gang.kafka.exception.kafkaexceptionback.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProduce {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Autowired
//    private ReplyingKafkaTemplate templateback;

//    @Autowired
//    private KafkaSendResultHandler producerListener;

    public void send() throws InterruptedException, ExecutionException {

        // -->
//        for (int i = 0; i < 3; i++) {
//            kafkaTemplate.send("start", "are you ok?" + "----" + i);
//        }

        // --> 异常处理
//        for (int i = 0; i < 3; i++) {
//            kafkaTemplate.send("error", "are you ok?" + "----" + i);
//        }

        // --> 结果回调
//        kafkaTemplate.setProducerListener(producerListener);
//        for (int i = 0; i < 3; i++) {
//            ListenableFuture back = kafkaTemplate.send("start", "are you ok?" + "----" + i);
//            logger.info("------> back is  :{}<-------", back);
//        }
//        Thread.sleep(5000);


        // --> 同步回调
//        try {
//            logger.info("------> {} <-------", kafkaTemplate.send("start", "are you ok?" + "----").get(10000, TimeUnit.MICROSECONDS));
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }

        // ReplyingKafkaTemplate 回调
//        ProducerRecord<String, String> record = new ProducerRecord<>("start", "is ok ");
//        RequestReplyFuture<String, String, String> replyFuture = templateback.sendAndReceive(record);
//        ConsumerRecord<String, String> consumerRecord = replyFuture.get();
//        logger.info("------> this is :{} <-------", consumerRecord.value());

        // partition
//        for (int i = 0; i < 3; i++) {
//            kafkaTemplate.send("start", "are you ok?" + "----" + i);
////            kafkaTemplate.send
//        }


    }


}

@Component
class KafkaSendResultHandler implements ProducerListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        logger.info("Message send success : " + producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord producerRecord, Exception exception) {
        logger.info("Message send error : " + producerRecord.toString());
    }
}
