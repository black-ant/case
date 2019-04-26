package com.mykafka.demo.config;

import com.mykafka.demo.service.ListenerMsg;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 17:18
 * @Version 1.0
 **/
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.topic.Name}")
    private String topicName;

    @Value("${spring.kafka.topic.numPartitions}")
    private int numPartitions;

    @Value("${spring.kafka.topic.replicationFactor}")
    private int replicationFactor;
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic myTopic() {
        //第三个参数是副本数量，确保集群中配置的数目大于等于副本数量
        return new NewTopic(topicName, numPartitions, (short) replicationFactor);
    }
}
