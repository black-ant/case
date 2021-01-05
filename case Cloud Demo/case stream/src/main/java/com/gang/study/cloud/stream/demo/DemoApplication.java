package com.gang.study.cloud.stream.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import para.cic.sync.common.api.SyncEventConsumer;
import para.cic.sync.common.api.SyncEventProducer;

@SpringBootApplication
//@EnableBinding(value = {SyncProduceTest.class, SyncConsumeTest.class})
//@EnableBinding(value = {SyncEventProducer.class, SyncEventConsumer.class})
@ComponentScan(basePackages = {"para.cic", "com.gang",}, lazyInit = true)
public class DemoApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
