package com.gang.study.cloud.stream.demo;

import com.gang.study.cloud.stream.demo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class DemoApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @StreamListener(Sink.INPUT)
    public void processVote(String person) {
        logger.info("------> Received :{} <-------", person.toString());
    }

}
