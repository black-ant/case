package com.gang.kafka.exception.kafkaexceptionback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * No Must config
 */
@Configuration
@Component
public class MyKafkaConfig {

//    /**
//     * back
//     *
//     * @param pf
//     * @param repliesContainer
//     * @return
//     */
//    @Bean
//    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(ProducerFactory<String, String> pf, ConcurrentMessageListenerContainer<String, String> repliesContainer) {
//        return new ReplyingKafkaTemplate(pf, repliesContainer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory() {
//        return new ConcurrentKafkaListenerContainerFactory();
//    }
//
//    @Bean
//    public ConcurrentMessageListenerContainer<String, String> repliesContainer(ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
//        ConcurrentMessageListenerContainer<String, String> repliesContainer = containerFactory.createContainer("replies");
//        repliesContainer.getContainerProperties().setGroupId("ant");
//        repliesContainer.setAutoStartup(false);
//        return repliesContainer;
//    }
}
