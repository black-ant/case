package com.gang.kafka.exception.kafkaexceptionback.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumeErrorHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "error", groupId = "ant", errorHandler = "consumerAwareErrorHandler")
    public void ListenerError(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in ListenerError :{}<-------", record.value());
        if ("are you ok?----0".equals(record.value())) {
            throw new NullPointerException();
        }
        logger.info("------> ListenerError ok :{}<-------", record.value());

    }

    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return new ConsumerAwareListenerErrorHandler() {

            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException e, Consumer<?, ?> consumer) {
                logger.info("consumerAwareErrorHandler receive : " + message.getPayload().toString());
                return "no handle";
            }
        };
    }

}
