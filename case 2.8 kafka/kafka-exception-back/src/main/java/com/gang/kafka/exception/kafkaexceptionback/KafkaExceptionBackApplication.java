package com.gang.kafka.exception.kafkaexceptionback;

import com.gang.kafka.exception.kafkaexceptionback.service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
//@EnableKafka
public class KafkaExceptionBackApplication {

    @Autowired
    private StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(KafkaExceptionBackApplication.class, args);
    }

}
